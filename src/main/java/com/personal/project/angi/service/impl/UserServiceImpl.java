package com.personal.project.angi.service.impl;

import co.elastic.clients.elasticsearch._types.FieldSort;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MultiMatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import co.elastic.clients.json.JsonData;
import com.personal.project.angi.constant.UploadConstant;
import com.personal.project.angi.enums.MessageResponseEnum;
import com.personal.project.angi.enums.ResponseCodeEnum;
import com.personal.project.angi.exception.response.ResponseBuilder;
import com.personal.project.angi.mapping.UserMapper;
import com.personal.project.angi.model.dto.MetaData;
import com.personal.project.angi.model.dto.ResponseDto;
import com.personal.project.angi.model.dto.request.FilterRequest;
import com.personal.project.angi.model.dto.request.SortRequest;
import com.personal.project.angi.model.dto.request.UserUpdateInfoRequest;
import com.personal.project.angi.model.dto.response.UserInfoResponse;
import com.personal.project.angi.model.dto.response.UserSearchResponse;
import com.personal.project.angi.model.enity.UserInfoModel;
import com.personal.project.angi.repository.UserRepository;
import com.personal.project.angi.service.FileService;
import com.personal.project.angi.service.UserElkService;
import com.personal.project.angi.service.UserService;
import com.personal.project.angi.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserElkService userElkService;
    private final FileService fileService;

    public ResponseEntity<ResponseDto<UserInfoResponse>> getUserInfo(String userId) {
        try {
            Optional<UserInfoModel> userInfoModel = userRepository.findById(userId);
            if (userInfoModel.isEmpty()) {
                return ResponseBuilder.badRequestResponse(
                        MessageResponseEnum.GET_USER_INFO_FAILED.getMessage(),
                        ResponseCodeEnum.GETUSER0200);
            }
            UserInfoResponse userInfoResponse = userMapper.toUserInfoResponse(userInfoModel.get());
            return ResponseBuilder.okResponse(
                    MessageResponseEnum.GET_USER_INFO_SUCCESS.getMessage(),
                    userInfoResponse,
                    ResponseCodeEnum.GETUSER1200
            );
        } catch (Exception e) {
            return ResponseBuilder.badRequestResponse(
                    MessageResponseEnum.GET_USER_INFO_FAILED.getMessage(),
                    ResponseCodeEnum.GETUSER0201);
        }
    }

    @Override
    public ResponseEntity<ResponseDto<UserInfoResponse>> updateUserInfo(String userId, UserUpdateInfoRequest userInfoRequest) {
        try {
            Optional<UserInfoModel> userInfoModel = userRepository.findById(userId);
            if (userInfoModel.isEmpty()) {
                return ResponseBuilder.badRequestResponse(
                        MessageResponseEnum.UPDATE_USER_INFO_FAILED.getMessage(),
                        ResponseCodeEnum.UPDATEUSER0200);
            }
            UserInfoModel userInfo = userInfoModel.get();

            try {
                userMapper.updateUserInfoModel(userInfo, userInfoRequest);
            } catch (Exception e) {
                return ResponseBuilder.badRequestResponse(
                        MessageResponseEnum.UPDATE_USER_INFO_FAILED.getMessage(),
                        ResponseCodeEnum.UPDATEUSER0201);
            }

            try {
                userRepository.save(userInfo);
                userElkService.saveOrUpdateUser(userInfo);
            } catch (Exception e) {
                return ResponseBuilder.badRequestResponse(
                        MessageResponseEnum.UPDATE_USER_INFO_FAILED.getMessage(),
                        ResponseCodeEnum.UPDATEUSER0202);
            }

            return ResponseBuilder.okResponse(
                    MessageResponseEnum.UPDATE_USER_INFO_SUCCESS.getMessage(),
                    userMapper.toUserInfoResponse(userInfo),
                    ResponseCodeEnum.UPDATEUSER1200
            );

        } catch (Exception e) {
            return ResponseBuilder.badRequestResponse(
                    MessageResponseEnum.UPDATE_USER_INFO_FAILED.getMessage(),
                    ResponseCodeEnum.UPDATEUSER0203);
        }
    }

    @Override
    public ResponseEntity<ResponseDto<Void>> updateUserAvatar(String userId, MultipartFile file) {
        try {
            Optional<UserInfoModel> userInfoModel = userRepository.findById(userId);
            if (userInfoModel.isEmpty()) {
                return ResponseBuilder.badRequestResponse(
                        MessageResponseEnum.UPDATE_USER_AVATAR_FAILED.getMessage(),
                        ResponseCodeEnum.UPDATEUSER0200);
            }

            Map imageUrl = null;
            if (file != null) {
                try {
                    imageUrl = fileService.uploadFile(file, userId, UploadConstant.USER_AVATAR);
                } catch (Exception e) {
                    return ResponseBuilder.badRequestResponse(
                            MessageResponseEnum.UPDATE_USER_AVATAR_FAILED.getMessage(),
                            ResponseCodeEnum.UPDATEUSER0201);
                }
            }

            try {
                UserInfoModel user = userInfoModel.get();
                if (file != null) {
                    user.setAvatarUrl(imageUrl.toString());
                    userRepository.save(user);
                    userElkService.saveOrUpdateUser(user);
                }
            }catch (Exception e){
                return ResponseBuilder.badRequestResponse(
                        MessageResponseEnum.UPDATE_USER_AVATAR_FAILED.getMessage(),
                        ResponseCodeEnum.UPDATEUSER0200);
            }

            return ResponseBuilder.okResponse(
                    MessageResponseEnum.UPDATE_USER_AVATAR_SUCCESS.getMessage(),
                    null,
                    ResponseCodeEnum.UPDATEUSERAVATAR1200
            );

        } catch (Exception e) {
            return ResponseBuilder.badRequestResponse(
                    MessageResponseEnum.UPDATE_USER_AVATAR_FAILED.getMessage(),
                    ResponseCodeEnum.UPDATEUSER0200);
        }
    }

    @Override
    public ResponseEntity<ResponseDto<List<UserSearchResponse>>> searchUser(int pageNo,
                                                                          int pageSize,
                                                                          String keyword,
                                                                          String sort,
                                                                          String filter) {
       try{
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

           try{
               Page<UserSearchResponse> userSearchResponses = userElkService.searchUser(boolQuery.build(),
                       sortOptions,
                       pageRequest);

               MetaData metaData = MetaData.builder()
                       .totalPage(userSearchResponses.getTotalPages())
                       .currentPage(userSearchResponses.getNumber())
                       .pageSize(userSearchResponses.getSize())
                       .build();

               return ResponseBuilder.okResponse(
                       MessageResponseEnum.SEARCH_USER_SUCCESS.getMessage(),
                       userSearchResponses.getContent(),
                       ResponseCodeEnum.SEARCHUSER1200,
                       metaData);
           }catch (Exception e){
                return ResponseBuilder.badRequestResponse(
                          MessageResponseEnum.SEARCH_USER_FAILED.getMessage(),
                          ResponseCodeEnum.GETUSER0201);
           }
       }catch (Exception e){
              return ResponseBuilder.badRequestResponse(
                     MessageResponseEnum.SEARCH_USER_FAILED.getMessage(),
                     ResponseCodeEnum.SEARCHUSER0200);
       }
    }
}
