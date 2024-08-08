package com.personal.project.angi.model.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateInfoRequest {
    String name;
    String avatarLink;
    String facebookLink;
    String instagramLink;
    String ward;
    String district;
    String city;
}
