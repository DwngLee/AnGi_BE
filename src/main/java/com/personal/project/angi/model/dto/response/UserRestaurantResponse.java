package com.personal.project.angi.model.dto.response;

import lombok.Data;

@Data
public class UserRestaurantResponse {
    private String id;
    private String name;
    private String avatarLink;
    private int points;
}
