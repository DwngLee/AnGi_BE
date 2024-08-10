package com.personal.project.angi.model.basemodel;

import com.personal.project.angi.enums.AccountStateEnum;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
@Data
public class UserInfoBaseModel {
    @Id
    private String id;

    private String username;

    private String password;

    private String name;

    private String phoneNumber;

    private String email;

    private String avatarUrl;

    private String facebookUrl;

    private String instagramUrl;

    private String ward;

    private String district;

    private String city;

    private LocalDate jointDate;

    private int points;

    private AccountStateEnum accountState;

    private Set<String> roles;

    @Field("createdAt")
    @CreatedDate
    private LocalDateTime createdAt;

    @Field("updatedAt")
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
