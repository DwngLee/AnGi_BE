package com.personal.project.angi.controller;

import com.personal.project.angi.model.dto.ResponseDto;
import com.personal.project.angi.model.dto.request.UserUpdateInfoRequest;
import com.personal.project.angi.model.dto.response.UserInfoResponse;
import com.personal.project.angi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/users")
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;
    @GetMapping("/{userId}")
    public ResponseEntity<ResponseDto<UserInfoResponse>> getUserInfo(@PathVariable String userId){
        return userService.getUserInfo(userId);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<ResponseDto<UserInfoResponse>> updateUserInfo(@PathVariable String userId,
                                                                        @RequestBody UserUpdateInfoRequest userInfoRequest){
        return userService.updateUserInfo(userId, userInfoRequest);
    }
}
