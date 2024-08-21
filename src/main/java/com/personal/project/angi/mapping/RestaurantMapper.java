package com.personal.project.angi.mapping;

import com.personal.project.angi.enums.RestaurantStateEnum;
import com.personal.project.angi.model.dto.request.RestaurantCreationRequest;
import com.personal.project.angi.model.dto.response.RestaurantResponse;
import com.personal.project.angi.model.enity.RestaurantElkModel;
import com.personal.project.angi.model.enity.RestaurantModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.math.BigDecimal;
import java.util.Locale;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {
    @Mapping(target = "restaurantState", constant = "PENDING")
    @Mapping(target = "hasAnOwner", constant = "false")
    @Mapping(target = "openTimeBaseModelList", source = "openTimeList")

    RestaurantModel toRestaurantModel(RestaurantCreationRequest request);

    @Mapping(target = "openTimeList", source = "openTimeBaseModelList")
    RestaurantResponse toRestaurantResponse(RestaurantModel model);

    @Mapping(target = "location", expression = "java(toGeoPoint(model.getLatitude(), model.getLongitude()))")
    RestaurantElkModel toRestaurantElkModel(RestaurantModel model);

    default GeoPoint toGeoPoint(BigDecimal latitude, BigDecimal longitude) {
        if (latitude == null || longitude == null) {
            return null;
        }
        return new GeoPoint(latitude.doubleValue(), longitude.doubleValue());
    }


}
