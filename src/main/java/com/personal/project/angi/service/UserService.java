package com.personal.project.angi.service;

import com.personal.project.angi.model.dto.ResponseDto;
import com.personal.project.angi.model.dto.request.UserUpdateInfoRequest;
import com.personal.project.angi.model.dto.response.UserInfoResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<ResponseDto<UserInfoResponse>> getUserInfo(String userId);

    ResponseEntity<ResponseDto<UserInfoResponse>> updateUserInfo(String userId, UserUpdateInfoRequest userInfoRequest);
}
