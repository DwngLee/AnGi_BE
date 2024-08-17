package com.personal.project.angi.controller;

import com.personal.project.angi.model.dto.ResponseDto;
import com.personal.project.angi.model.dto.request.RefreshTokenRequest;
import com.personal.project.angi.model.dto.request.UserLoginRequest;
import com.personal.project.angi.model.dto.request.UserRegisterRequest;
import com.personal.project.angi.model.dto.response.JwtResponse;
import com.personal.project.angi.service.AuthenticateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticateController {
    private final AuthenticateService authenticateService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto<Void>> register(@RequestBody UserRegisterRequest request){
        return authenticateService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<JwtResponse>> login(@RequestBody UserLoginRequest request){
        return authenticateService.login(request);
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseDto<Void>> login(){
        return authenticateService.logout();
    }


    @PostMapping("/refresh")
    public ResponseEntity<ResponseDto<JwtResponse>> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return authenticateService.refreshToken(refreshTokenRequest.getRefreshToken());
    }


}
