package com.personal.project.angi.service;

import com.personal.project.angi.model.dto.ResponseDto;
import com.personal.project.angi.model.dto.request.RestaurantCreationRequest;
import com.personal.project.angi.model.dto.request.RestaurantUpdateRequest;
import com.personal.project.angi.model.dto.response.RestaurantResponse;
import org.springframework.http.ResponseEntity;

public interface RestaurantService {
    ResponseEntity<ResponseDto<Void>> createRestaurant(RestaurantCreationRequest request);

    ResponseEntity<ResponseDto<RestaurantResponse>> getRestaurantById(String id);

    ResponseEntity<ResponseDto<Void>> updateRestaurant(String id, RestaurantUpdateRequest request);
}
