package com.personal.project.angi.model.basemodel;

import com.personal.project.angi.enums.RestaurantStateEnum;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.math.BigDecimal;
import java.util.List;

@Data
public class RestaurantElkBaseModel {
    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String restaurantName;

    @Field(type = FieldType.Text)
    private String houseNumber;

    @Field(type = FieldType.Text)
    private String ward;

    @Field(type = FieldType.Text)
    private String district;

    @Field(type = FieldType.Text)
    private String city;

    @GeoPointField
    private GeoPoint location;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Boolean)
    private Boolean offerDelivery;

    @Field(type = FieldType.Boolean)
    private Boolean offerTakeaway;

    @Field(type = FieldType.Boolean)
    private Boolean outdoorSeating;

    @Field(type = FieldType.Nested)
    private List<OpenTimeBaseModel> openTimeBaseModelList;

    @Field(type = FieldType.Nested)
    private List<TagBaseModel> tagBaseModelList;

    @Field(type = FieldType.Nested)
    private List<String> restaurantImageUrlList;
}
