package com.personal.project.angi.model.dto.request;

import lombok.Data;

@Data
public class UserLoginRequest {
    private String username;
    private String password;
}
