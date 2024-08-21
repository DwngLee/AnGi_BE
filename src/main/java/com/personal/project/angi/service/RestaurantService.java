package com.personal.project.angi.service;

import com.personal.project.angi.model.dto.ResponseDto;
import com.personal.project.angi.model.dto.request.RestaurantCreationRequest;
import com.personal.project.angi.model.dto.response.RestaurantResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RestaurantService {
    ResponseEntity<ResponseDto<Void>> createRestaurant(RestaurantCreationRequest request);
}
