package com.personal.project.angi.controller;

import com.personal.project.angi.model.dto.ResponseDto;
import com.personal.project.angi.model.dto.request.UserUpdateInfoRequest;
import com.personal.project.angi.model.dto.response.UserInfoResponse;
import com.personal.project.angi.model.dto.response.UserSearchResponse;
import com.personal.project.angi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/users")
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;
    @GetMapping("/{userId}")
    public ResponseEntity<ResponseDto<UserInfoResponse>> getUserInfo(@PathVariable String userId) {
        return userService.getUserInfo(userId);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<ResponseDto<UserInfoResponse>> updateUserInfo(@PathVariable String userId,
                                                                        @RequestBody UserUpdateInfoRequest userInfoRequest) {
        return userService.updateUserInfo(userId, userInfoRequest);
    }

    @PostMapping(value = "/{userId}/avatar",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<Void>> updateUserAvatar(@PathVariable String userId,
                                                              @RequestParam MultipartFile file) {
        return userService.updateUserAvatar(userId, file);
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseDto<List<UserSearchResponse>>> searchUser(@RequestParam(required = false, defaultValue = "0") int pageNo,
                                                                            @RequestParam(required = false, defaultValue = "10") int pageSize,
                                                                            @RequestParam(required = false) String keyword,
                                                                            @RequestParam(required = false) String sort,
                                                                            @RequestParam(required = false) String filter) {
        pageNo = pageNo < 0 ? 0 : pageNo;
        pageSize = pageSize <= 0 ? 10 : pageSize;
        return userService.searchUser(pageNo, pageSize, keyword, sort, filter);
    }
}
