package com.personal.project.angi.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtResponse {
    private String accessToken;
    private String refreshToken;
    private String type = "Bearer ";
}
