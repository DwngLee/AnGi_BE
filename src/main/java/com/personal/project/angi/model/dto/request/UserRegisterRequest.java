package com.personal.project.angi.model.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRegisterRequest {
    String username;
    String password;
    String repeatPassword;
    String name;
    String email;
    String ward;
    String district;
    String city;
}
