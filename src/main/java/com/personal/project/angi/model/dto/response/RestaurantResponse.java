package com.personal.project.angi.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.personal.project.angi.enums.RestaurantStateEnum;
import com.personal.project.angi.model.dto.OpenTimeDto;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class RestaurantResponse {
    private String id;

    private String restaurantName;

    private String houseNumber;

    private String ward;

    private String district;

    private String city;

    private BigDecimal longitude;

    private BigDecimal latitude;

    private String description;

    private String phoneNumber;

    private String email;

    private String website;

    private String facebookLink;

    private String instagramLink;

    private RestaurantStateEnum restaurantState;

    private Boolean hasAnOwner;

    private Boolean offerDelivery;

    private Boolean offerTakeaway;

    private Boolean outdoorSeating;

    private UserRestaurantResponse userAdd;

    private UserRestaurantResponse userUpdate;

    @JsonProperty("openTimeList")
    private List<OpenTimeDto> openTimeList;

    @JsonProperty("tagList")
    private List<TagResponse> tagList;

    private List<String> restaurantImageUrlList;

    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;

    private double point;
}
