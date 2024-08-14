package com.personal.project.angi.service;

import com.personal.project.angi.model.dto.ResponseDto;
import com.personal.project.angi.model.dto.request.UpdateUserAvatarRequest;
import com.personal.project.angi.model.dto.request.UserUpdateInfoRequest;
import com.personal.project.angi.model.dto.response.UserInfoResponse;
import com.personal.project.angi.model.dto.response.UserSearchResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    ResponseEntity<ResponseDto<UserInfoResponse>> getUserInfo(String userId);

    ResponseEntity<ResponseDto<UserInfoResponse>> updateUserInfo(String userId, UserUpdateInfoRequest userInfoRequest);

    ResponseEntity<ResponseDto<Void>> updateUserAvatar(String userId, MultipartFile file);
    ResponseEntity<ResponseDto<List<UserSearchResponse>>> searchUser(int pageNo, int pageSize, String keyword, String sort, String filter);
}
