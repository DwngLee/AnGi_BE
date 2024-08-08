package com.personal.project.angi.model.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@FieldDefaults(level = PRIVATE)
public class UserInfoResponse {
    String id;
    String name;
    String email;
    String avatarLink;
    String facebookLink;
    String instagramLink;
    String city;
    LocalDate joinDate;
    int points;
}
