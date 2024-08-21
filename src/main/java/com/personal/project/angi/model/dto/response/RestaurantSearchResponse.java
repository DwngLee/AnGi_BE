package com.personal.project.angi.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.util.List;

@Data
public class RestaurantSearchResponse {
    private String id;

    private String restaurantName;

    private String houseNumber;

    private String ward;

    private String district;

    private String city;

    private GeoPoint location;

    private Boolean offerDelivery;

    private Boolean offerTakeaway;

    private Boolean outdoorSeating;

    @JsonProperty("tagList")
    private List<TagResponse> tagList;

    private List<String> restaurantImageUrlList;
}
