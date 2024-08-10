package com.personal.project.angi.model.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class UpdateUserAvatarRequest {
    MultipartFile avatarImage;
}
