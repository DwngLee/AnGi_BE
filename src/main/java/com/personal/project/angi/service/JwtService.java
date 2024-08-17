package com.personal.project.angi.service;

import com.personal.project.angi.model.dto.response.JwtResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface JwtService {
    JwtResponse generateToken(Authentication authentication);
    boolean validateToken(String token);
    boolean validateRefreshToken(String refreshToken);
    String getTokenFromRequest(HttpServletRequest request);
    String getUserIdFromToken(String token);
    void revokeRefreshToken(String refreshToken);
    void revokeAccessToken(String accessToken);
    String getUserIdFromRefreshToken(String refreshToken);




}
