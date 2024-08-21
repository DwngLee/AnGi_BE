package com.personal.project.angi.service.impl;

import com.personal.project.angi.configuration.security.UserDetailsImpl;
import com.personal.project.angi.constant.UploadConstant;
import com.personal.project.angi.enums.MessageResponseEnum;
import com.personal.project.angi.enums.ResponseCodeEnum;
import com.personal.project.angi.exception.response.ResponseBuilder;
import com.personal.project.angi.mapping.RestaurantMapper;
import com.personal.project.angi.mapping.TagMapper;
import com.personal.project.angi.mapping.UserMapper;
import com.personal.project.angi.model.dto.ResponseDto;
import com.personal.project.angi.model.dto.request.RestaurantCreationRequest;
import com.personal.project.angi.model.dto.response.RestaurantResponse;
import com.personal.project.angi.model.enity.RestaurantElkModel;
import com.personal.project.angi.model.enity.RestaurantModel;
import com.personal.project.angi.model.enity.TagModel;
import com.personal.project.angi.model.enity.UserInfoModel;
import com.personal.project.angi.repository.RestaurantRepository;
import com.personal.project.angi.service.*;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
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
            restaurantElkModel.setTagBaseModelList(tagList);

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
}
