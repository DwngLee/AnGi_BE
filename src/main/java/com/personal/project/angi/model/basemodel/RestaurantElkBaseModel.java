package com.personal.project.angi.model.basemodel;

import com.personal.project.angi.model.dto.OpenTimeSearchDto;
import com.personal.project.angi.model.dto.response.TagResponse;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

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
//
    @Field(type = FieldType.Nested)
    private List<OpenTimeSearchDto> openTimeList;

    @Field(type = FieldType.Nested)
    private List<TagResponse> tagList;

    @Field(type = FieldType.Text)
    private List<String> restaurantImageUrlList;
}
