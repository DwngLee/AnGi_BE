package com.personal.project.angi.service;

import com.personal.project.angi.model.dto.ResponseDto;
import com.personal.project.angi.model.dto.request.UserLoginRequest;
import com.personal.project.angi.model.dto.request.UserRegisterRequest;
import com.personal.project.angi.model.dto.response.JwtResponse;
import org.springframework.http.ResponseEntity;

public interface AuthenticateService {
    ResponseEntity<ResponseDto<Void>> register(UserRegisterRequest request);
    ResponseEntity<ResponseDto<JwtResponse>> login(UserLoginRequest request);
    ResponseEntity<ResponseDto<Void>> logout();
    ResponseEntity<ResponseDto<JwtResponse>> refreshToken(String refreshToken);

}
