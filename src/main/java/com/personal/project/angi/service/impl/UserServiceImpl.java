package com.personal.project.angi.service.impl;

import com.personal.project.angi.enums.ResponseCodeEnum;
import com.personal.project.angi.exception.response.ResponseBuilder;
import com.personal.project.angi.mapping.UserMapper;
import com.personal.project.angi.model.dto.ResponseDto;
import com.personal.project.angi.model.dto.request.UserUpdateInfoRequest;
import com.personal.project.angi.model.dto.response.UserInfoResponse;
import com.personal.project.angi.model.enity.UserInfoModel;
import com.personal.project.angi.repository.UserRepository;
import com.personal.project.angi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    public ResponseEntity<ResponseDto<UserInfoResponse>> getUserInfo(String userId) {
        try{
            Optional<UserInfoModel> userInfoModel = userRepository.findById(userId);
            if(userInfoModel.isEmpty()){
                return ResponseBuilder.badRequestResponse(
                        ResponseCodeEnum.GETUSER0200.getMessage(),
                        ResponseCodeEnum.GETUSER0200);
            }
            UserInfoResponse userInfoResponse = userMapper.toUserInfoResponse(userInfoModel.get());
            return ResponseBuilder.okResponse(
                    ResponseCodeEnum.GETUSER1200.getMessage(),
                    userInfoResponse,
                    ResponseCodeEnum.GETUSER1200
            );
        }catch (Exception e){
            return ResponseBuilder.badRequestResponse(
                    ResponseCodeEnum.GETUSER0201.getMessage(),
                    ResponseCodeEnum.GETUSER0201);
        }
    }

    @Override
    public ResponseEntity<ResponseDto<UserInfoResponse>> updateUserInfo(String userId, UserUpdateInfoRequest userInfoRequest) {
        try{
            Optional<UserInfoModel> userInfoModel = userRepository.findById(userId);
            if(userInfoModel.isEmpty()){
                return ResponseBuilder.badRequestResponse(
                        ResponseCodeEnum.GETUSER0200.getMessage(),
                        ResponseCodeEnum.GETUSER0200);
            }
            UserInfoModel userInfo = userInfoModel.get();

        }catch (Exception e){
            return ResponseBuilder.badRequestResponse(
                    ResponseCodeEnum.UPDATEUSER0201.getMessage(),
                    ResponseCodeEnum.UPDATEUSER0201);
        }
    }
}
