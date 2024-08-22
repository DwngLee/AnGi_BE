package com.personal.project.angi.service.impl;

import co.elastic.clients.elasticsearch._types.FieldSort;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MultiMatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import co.elastic.clients.json.JsonData;
import com.personal.project.angi.configuration.security.UserDetailsImpl;
import com.personal.project.angi.constant.UploadConstant;
import com.personal.project.angi.enums.MessageResponseEnum;
import com.personal.project.angi.enums.ResponseCodeEnum;
import com.personal.project.angi.exception.response.ResponseBuilder;
import com.personal.project.angi.mapping.RestaurantMapper;
import com.personal.project.angi.mapping.TagMapper;
import com.personal.project.angi.mapping.UserMapper;
import com.personal.project.angi.model.dto.MetaData;
import com.personal.project.angi.model.dto.ResponseDto;
import com.personal.project.angi.model.dto.request.FilterRequest;
import com.personal.project.angi.model.dto.request.RestaurantCreationRequest;
import com.personal.project.angi.model.dto.request.RestaurantUpdateRequest;
import com.personal.project.angi.model.dto.request.SortRequest;
import com.personal.project.angi.model.dto.response.RestaurantResponse;
import com.personal.project.angi.model.dto.response.RestaurantSearchResponse;
import com.personal.project.angi.model.enity.RestaurantElkModel;
import com.personal.project.angi.model.enity.RestaurantModel;
import com.personal.project.angi.model.enity.TagModel;
import com.personal.project.angi.model.enity.UserInfoModel;
import com.personal.project.angi.repository.RestaurantRepository;
import com.personal.project.angi.service.*;
import com.personal.project.angi.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantMapper restaurantMapper;
    private final RestaurantRepository restaurantRepository;
    private final FileService fileService;
    private final TagService tagService;
    private final RestaurantElkService restaurantElkService;
    private final UserInfoService userService;

    private final TagMapper tagMapper;
    private final UserMapper userMapper;

    @Override
    public ResponseEntity<ResponseDto<Void>> createRestaurant(RestaurantCreationRequest request) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (userDetails == null) {
                return ResponseBuilder.badRequestResponse(
                        MessageResponseEnum.CREATE_RESTAURANT_FAILED.getMessage(),
                        ResponseCodeEnum.CREATERESTAURANT0201);
            }
            UserInfoModel user = userDetails.getUser();
            RestaurantModel restaurantModel = null;

            //Generate id for create file path
            String restaurantId = ObjectId.get().toString();

            try {
                restaurantModel = restaurantMapper.toRestaurantModel(request);
            } catch (Exception e) {
                return ResponseBuilder.badRequestResponse(
                        MessageResponseEnum.CREATE_RESTAURANT_FAILED.getMessage(),
                        ResponseCodeEnum.CREATERESTAURANT0202);
            }

            restaurantModel.setId(restaurantId);
            restaurantModel.setUserAddId(user.getId());
            restaurantModel.setUserUpdateId(user.getId());

            //Upload images
            List<String> imageUrlList = new ArrayList<>();
            if (request.getRestaurantImageList() != null) {
                try {
                    List<Map> results = fileService.uploadMultiFiles(request.getRestaurantImageList(), restaurantId, UploadConstant.RESTAURANT_IMAGE);

                    for (Map result : results) {
                        imageUrlList.add((String) result.get("url"));
                    }
                } catch (Exception e) {
                    return ResponseBuilder.badRequestResponse(
                            MessageResponseEnum.CREATE_RESTAURANT_FAILED.getMessage(),
                            ResponseCodeEnum.CREATERESTAURANT0203);
                }
                restaurantModel.setRestaurantImageUrlList(imageUrlList);
            }

            //Find and add tags
            List<TagModel> tagList = getTagModelList(request.getTagIdList());

            RestaurantElkModel restaurantElkModel = restaurantMapper.toRestaurantElkModel(restaurantModel);
            restaurantElkModel.setTagList(tagMapper.toTagResponseList(tagList));

            try {
                restaurantRepository.save(restaurantModel);
                restaurantElkService.saveOrUpdateRestaurant(restaurantElkModel);

            } catch (Exception e) {
                return ResponseBuilder.badRequestResponse(
                        MessageResponseEnum.CREATE_RESTAURANT_FAILED.getMessage(),
                        ResponseCodeEnum.CREATERESTAURANT0204);
            }
            return ResponseBuilder.okResponse(
                    MessageResponseEnum.CREATE_RESTAURANT_SUCCESS.getMessage(),
                    ResponseCodeEnum.CREATERESTAURANT1200);


        } catch (Exception e) {
            return ResponseBuilder.badRequestResponse(
                    MessageResponseEnum.CREATE_RESTAURANT_FAILED.getMessage(),
                    ResponseCodeEnum.CREATERESTAURANT0200);
        }
    }

    @Override
    public ResponseEntity<ResponseDto<RestaurantResponse>> getRestaurantById(String id) {
        try {
            RestaurantModel restaurantModel = restaurantRepository.findByIdAndRestaurantStateIs(id, "ACTIVE");
            if (restaurantModel == null) {
                return ResponseBuilder.badRequestResponse(
                        MessageResponseEnum.GET_RESTAURANT_FAILED.getMessage(),
                        ResponseCodeEnum.GETRESTAURANT0201);
            }
            UserInfoModel userAdd = userService.getUserModel(restaurantModel.getUserAddId());
            if (userAdd == null) {
                return ResponseBuilder.badRequestResponse(
                        MessageResponseEnum.GET_RESTAURANT_FAILED.getMessage(),
                        ResponseCodeEnum.GETRESTAURANT0202);
            }
            UserInfoModel userUpdate = userService.getUserModel(restaurantModel.getUserUpdateId());
            if (userUpdate == null) {
                return ResponseBuilder.badRequestResponse(
                        MessageResponseEnum.GET_RESTAURANT_FAILED.getMessage(),
                        ResponseCodeEnum.GETRESTAURANT0203);
            }

            List<TagModel> tagList = getTagModelList(restaurantModel.getTagIdList());

            RestaurantResponse restaurantResponse = restaurantMapper.toRestaurantResponse(restaurantModel);

            restaurantResponse.setTagList(tagMapper.toTagResponseList(tagList));
            restaurantResponse.setUserAdd(userMapper.toUserRestaurantResponse(userAdd));
            restaurantResponse.setUserUpdate(userMapper.toUserRestaurantResponse(userUpdate));

            return ResponseBuilder.okResponse(
                    MessageResponseEnum.GET_RESTAURANT_SUCCESS.getMessage(),
                    restaurantResponse,
                    ResponseCodeEnum.CREATERESTAURANT1200);
        } catch (Exception e) {
            return ResponseBuilder.badRequestResponse(
                    MessageResponseEnum.GET_RESTAURANT_FAILED.getMessage(),
                    ResponseCodeEnum.GETRESTAURANT0200);
        }
    }

    //#TODO: Change to RestaurantUpdateBaseModel
    @Override
    public ResponseEntity<ResponseDto<Void>> updateRestaurant(String id, RestaurantUpdateRequest newData) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (userDetails == null) {
                return ResponseBuilder.badRequestResponse(
                        MessageResponseEnum.UPDATE_RESTAURANT_FAILED.getMessage(),
                        ResponseCodeEnum.UPDATERESTAURANT0201);
            }
            UserInfoModel userUpdate = userDetails.getUser();
            RestaurantModel oldData = restaurantRepository.findByIdAndRestaurantStateIs(id, "ACTIVE");
            if (oldData == null) {
                return ResponseBuilder.badRequestResponse(
                        MessageResponseEnum.UPDATE_RESTAURANT_FAILED.getMessage(),
                        ResponseCodeEnum.UPDATERESTAURANT0202);
            }
            List<TagModel> tagList = getTagModelList(newData.getTagIdList());
            RestaurantModel newRestaurantData = null;

            try {
                newRestaurantData = migrateRestaurantData(newData, oldData, userUpdate);
            } catch (Exception e) {
                return ResponseBuilder.badRequestResponse(
                        MessageResponseEnum.UPDATE_RESTAURANT_FAILED.getMessage(),
                        ResponseCodeEnum.UPDATERESTAURANT0203);
            }

            RestaurantElkModel restaurantElkModel = restaurantMapper.toRestaurantElkModel(newRestaurantData);
            restaurantElkModel.setTagList(tagMapper.toTagResponseList(tagList));

            try {
                restaurantRepository.save(newRestaurantData);
                restaurantElkService.saveOrUpdateRestaurant(restaurantElkModel);
                return ResponseBuilder.okResponse(
                        MessageResponseEnum.UPDATE_RESTAURANT_SUCCESS.getMessage(),
                        ResponseCodeEnum.UPDATERESTAURANT1200);
            } catch (Exception e) {
                return ResponseBuilder.badRequestResponse(
                        MessageResponseEnum.UPDATE_RESTAURANT_FAILED.getMessage(),
                        ResponseCodeEnum.UPDATERESTAURANT0204);
            }

        } catch (Exception e) {
            return ResponseBuilder.badRequestResponse(
                    MessageResponseEnum.UPDATE_RESTAURANT_FAILED.getMessage(),
                    ResponseCodeEnum.UPDATERESTAURANT0200);
        }
    }

    @Override
    public ResponseEntity<ResponseDto<List<RestaurantSearchResponse>>> searchRestaurant(int pageNo,
                                                                                        int pageSize,
                                                                                        String keyword,
                                                                                        String sort,
                                                                                        String filter) {
        try {
            Object temp = SecurityContextHolder.getContext().getAuthentication();
            BoolQuery.Builder boolQuery = new BoolQuery.Builder();
            List<SortOptions> sortOptions = new ArrayList<>();
            PageRequest pageRequest = PageRequest.of(pageNo, pageSize);

            //#Todo: Refactor code to reduce boiler code
            if (keyword != null && !keyword.isEmpty()) {
                boolQuery.must(MultiMatchQuery.of(m -> m
                                .query(keyword))
                        ._toQuery());
            }

            //get sort options
            if (sort != null && !sort.isEmpty()) {
                List<SortRequest> sortRequestList = Util.parseSortRequest(sort);
                for (SortRequest sortRequest : sortRequestList) {
                    String sortField = sortRequest.getSortField();
                    sortOptions.add(new SortOptions.Builder()
                            .field(new FieldSort.Builder()
                                    .field(sortField)
                                    .order(sortRequest.getSortDirection())
                                    .build())
                            .build());
                }
            }

            //get filter options
            if (filter != null && !filter.isEmpty()) {
                List<FilterRequest> filterRequestList = Util.parseFilterRequest(filter);
                for (FilterRequest filterRequest : filterRequestList) {
                    switch (filterRequest.getFilterOperations()) {
                        case "gte" -> boolQuery.filter(RangeQuery.of(r -> r
                                        .field(filterRequest.getFilterField())
                                        .gte(JsonData.of(filterRequest.getFliterValue())))
                                ._toQuery());
                        case "lte" -> boolQuery.filter(RangeQuery.of(r -> r
                                        .field(filterRequest.getFilterField())
                                        .lte(JsonData.of(filterRequest.getFliterValue())))
                                ._toQuery());
                        case "eq" -> boolQuery.filter(TermQuery.of(t -> t
                                        .field(filterRequest.getFilterField())
                                        .value(FieldValue.of(filterRequest.getFliterValue())))
                                ._toQuery());
                    }
                }
            }

            try {
                Page<RestaurantSearchResponse> restaurantSearchResponses = restaurantElkService.searchRestaurant(boolQuery.build(),
                        sortOptions,
                        pageRequest);

                MetaData metaData = MetaData.builder()
                        .totalPage(restaurantSearchResponses.getTotalPages())
                        .currentPage(restaurantSearchResponses.getNumber())
                        .pageSize(restaurantSearchResponses.getSize())
                        .build();

                return ResponseBuilder.okResponse(
                        MessageResponseEnum.SEARCH_RESTAURANT_SUCCESS.getMessage(),
                        restaurantSearchResponses.getContent(),
                        ResponseCodeEnum.SEARCHRESTAURANT1200,
                        metaData);

            } catch (Exception e) {
                return ResponseBuilder.badRequestResponse(
                        MessageResponseEnum.SEARCH_RESTAURANT_FAILED.getMessage(),
                        ResponseCodeEnum.SEARCHRESTAURANT0201);
            }


        } catch (Exception e) {
            return ResponseBuilder.badRequestResponse(
                    MessageResponseEnum.SEARCH_RESTAURANT_FAILED.getMessage(),
                    ResponseCodeEnum.SEARCHRESTAURANT0200);
        }
    }


    private List<TagModel> getTagModelList(List<String> tagIdList) {
        List<TagModel> tagList = new ArrayList<>();
        for (String tagId : tagIdList) {
            TagModel tag = tagService.getTagModel(tagId);
            if (tag != null) {
                tagList.add(tag);
            }
        }
        return tagList;
    }

    private RestaurantModel migrateRestaurantData(RestaurantUpdateRequest newData, RestaurantModel oldData, UserInfoModel userUpdate) {
        try {
            RestaurantModel newRestaurant = restaurantMapper.toRestaurantModel(newData);
            newRestaurant.setId(oldData.getId());
            newRestaurant.setUserAddId(oldData.getUserAddId());
            newRestaurant.setUserUpdateId(userUpdate.getId());
            newRestaurant.setRestaurantImageUrlList(oldData.getRestaurantImageUrlList());
            newRestaurant.setPoint(oldData.getPoint());
            newRestaurant.setCreatedAt(oldData.getCreatedAt());
            newRestaurant.setUpdatedAt(LocalDateTime.now());
            return newRestaurant;
        } catch (Exception e) {
            log.error("Error when migrate data", e);
            throw e;
        }
    }
}
