package com.personal.project.angi.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSearchResponse {
    private String id;
    private String name;
    private String city;
    private String avatarUrl;
}
