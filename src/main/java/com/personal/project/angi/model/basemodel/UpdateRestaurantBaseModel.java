package com.personal.project.angi.model.basemodel;

import com.personal.project.angi.enums.RestaurantStateEnum;
import com.personal.project.angi.enums.UpdateStateEnum;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class UpdateRestaurantBaseModel {
    @Id
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

    private UpdateStateEnum updateState;

    private Boolean offerDelivery;

    private Boolean offerTakeaway;

    private Boolean outdoorSeating;

    private String userId;

    private String restaurantId;

    private List<OpenTimeBaseModel> openTimeBaseModelList;

    private List<TagBaseModel> tagBaseModelList;

    @Field("createdAt")
    @CreatedDate
    private LocalDateTime createdAt;

    @Field("updatedAt")
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
