package com.personal.project.angi.service;

import com.personal.project.angi.model.dto.ResponseDto;
import com.personal.project.angi.model.dto.request.UserUpdateInfoRequest;
import com.personal.project.angi.model.dto.response.UserInfoResponse;
import com.personal.project.angi.model.dto.response.UserSearchResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserInfoService {
    ResponseEntity<ResponseDto<UserInfoResponse>> getUserInfo(String userId);

    ResponseEntity<ResponseDto<UserInfoResponse>> updateUserInfo(UserUpdateInfoRequest userInfoRequest);

    ResponseEntity<ResponseDto<Void>> updateUserAvatar(MultipartFile file);

    ResponseEntity<ResponseDto<List<UserSearchResponse>>> searchUser(int pageNo, int pageSize, String keyword, String sort, String filter);
}
