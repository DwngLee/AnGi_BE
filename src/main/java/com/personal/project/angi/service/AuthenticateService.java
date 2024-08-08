package com.personal.project.angi.service;

import com.personal.project.angi.model.dto.ResponseDto;
import com.personal.project.angi.model.dto.request.UserRegisterRequest;
import org.springframework.http.ResponseEntity;

public interface AuthenticateService {
    public ResponseEntity<ResponseDto<Void>> register(UserRegisterRequest request);
}
