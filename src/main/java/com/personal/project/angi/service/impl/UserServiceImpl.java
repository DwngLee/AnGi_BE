package com.personal.project.angi.service.impl;

import com.personal.project.angi.constant.UploadConstant;
import com.personal.project.angi.enums.MessageResponseEnum;
import com.personal.project.angi.enums.ResponseCodeEnum;
import com.personal.project.angi.exception.response.ResponseBuilder;
import com.personal.project.angi.mapping.UserMapper;
import com.personal.project.angi.model.dto.ResponseDto;
import com.personal.project.angi.model.dto.request.UserUpdateInfoRequest;
import com.personal.project.angi.model.dto.response.UserInfoResponse;
import com.personal.project.angi.model.enity.UserInfoModel;
import com.personal.project.angi.repository.UserRepository;
import com.personal.project.angi.service.FileService;
import com.personal.project.angi.service.UserElkService;
import com.personal.project.angi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
}
