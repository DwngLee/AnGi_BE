package com.personal.project.angi.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.personal.project.angi.model.dto.OpenTimeDto;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
public class RestaurantCreationRequest {
    private String restaurantName;

    private String houseNumber;

    private String ward;

    private String district;

    private String city;

    private String longitude;

    private String latitude;

    private String description;

    private String phoneNumber;

    private String email;

    private String website;

    private String facebookLink;

    private String instagramLink;

    private Boolean offerDelivery;

    private Boolean offerTakeaway;

    private Boolean outdoorSeating;

    @JsonProperty("openTimeList")
    private List<OpenTimeDto> openTimeList;

    @JsonProperty("tagIdList")
    private List<String> tagIdList;

    private MultipartFile[] restaurantImageList;

}
