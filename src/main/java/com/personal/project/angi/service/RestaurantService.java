package com.personal.project.angi.service;

import com.personal.project.angi.model.dto.ResponseDto;
import com.personal.project.angi.model.dto.request.RestaurantCreationRequest;
import com.personal.project.angi.model.dto.request.RestaurantUpdateRequest;
import com.personal.project.angi.model.dto.response.RestaurantResponse;
import com.personal.project.angi.model.dto.response.RestaurantSearchResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RestaurantService {
    ResponseEntity<ResponseDto<Void>> createRestaurant(RestaurantCreationRequest request);

    ResponseEntity<ResponseDto<RestaurantResponse>> getRestaurantById(String id);

    ResponseEntity<ResponseDto<Void>> updateRestaurant(String id, RestaurantUpdateRequest request);

    ResponseEntity<ResponseDto<List<RestaurantSearchResponse>>> searchRestaurant(int pageNo, int pageSize, String keyword, String sort, String filter);
}
