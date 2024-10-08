package com.personal.project.angi.controller;

import com.personal.project.angi.model.dto.ResponseDto;
import com.personal.project.angi.model.dto.request.UserUpdateInfoRequest;
import com.personal.project.angi.model.dto.response.UserInfoResponse;
import com.personal.project.angi.model.dto.response.UserSearchResponse;
import com.personal.project.angi.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/users")
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserInfoService userInfoService;

    @GetMapping("/{userId}")
    public ResponseEntity<ResponseDto<UserInfoResponse>> getUserInfo(@PathVariable String userId) {
        return userInfoService.getUserInfo(userId);
    }

    @PatchMapping("/myInfo")
    public ResponseEntity<ResponseDto<UserInfoResponse>> updateUserInfo(
            @RequestBody UserUpdateInfoRequest userInfoRequest) {
        return userInfoService.updateUserInfo(userInfoRequest);
    }

    @PostMapping(value = "/myInfo/avatar",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<Void>> updateUserAvatar(
            @RequestParam MultipartFile file) {
        return userInfoService.updateUserAvatar(file);
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseDto<List<UserSearchResponse>>> searchUser(@RequestParam(required = false, defaultValue = "0") int pageNo,
                                                                            @RequestParam(required = false, defaultValue = "10") int pageSize,
                                                                            @RequestParam(required = false) String keyword,
                                                                            @RequestParam(required = false) String sort,
                                                                            @RequestParam(required = false) String filter) {
        pageNo = pageNo < 0 ? 0 : pageNo;
        pageSize = pageSize <= 0 ? 10 : pageSize;
        return userInfoService.searchUser(pageNo, pageSize, keyword, sort, filter);
    }
}
