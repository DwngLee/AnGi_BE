package com.personal.project.angi.controller;

import com.personal.project.angi.model.dto.ResponseDto;
import com.personal.project.angi.model.dto.request.RestaurantCreationRequest;
import com.personal.project.angi.model.dto.request.RestaurantUpdateRequest;
import com.personal.project.angi.model.dto.response.RestaurantResponse;
import com.personal.project.angi.model.dto.response.RestaurantSearchResponse;
import com.personal.project.angi.model.dto.response.UserSearchResponse;
import com.personal.project.angi.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<Void>> createRestaurant(@ModelAttribute RestaurantCreationRequest request) {
        return restaurantService.createRestaurant(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<RestaurantResponse>> getRestaurantById(@PathVariable String id) {
        return restaurantService.getRestaurantById(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> updateRestaurant(@PathVariable String id, @RequestBody RestaurantUpdateRequest request) {
        return restaurantService.updateRestaurant(id, request);
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseDto<List<RestaurantSearchResponse>>> searchUser(@RequestParam(required = false, defaultValue = "0") int pageNo,
                                                                                  @RequestParam(required = false, defaultValue = "10") int pageSize,
                                                                                  @RequestParam(required = false) String keyword,
                                                                                  @RequestParam(required = false) String sort,
                                                                                  @RequestParam(required = false) String filter,
                                                                                  @RequestParam(required = false) String lat,
                                                                                  @RequestParam(required = false) String lon,
                                                                                  @RequestParam(required = false) String radius) {
        pageNo = pageNo < 0 ? 0 : pageNo;
        pageSize = pageSize <= 0 ? 10 : pageSize;
        return restaurantService.searchRestaurant(pageNo, pageSize, keyword, sort, filter, lat, lon, radius);
    }
}
