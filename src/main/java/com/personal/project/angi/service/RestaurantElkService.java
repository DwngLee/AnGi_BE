package com.personal.project.angi.service;

import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import com.personal.project.angi.model.dto.response.UserSearchResponse;
import com.personal.project.angi.model.enity.RestaurantElkModel;
import com.personal.project.angi.model.enity.RestaurantModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.io.IOException;
import java.util.List;

public interface RestaurantElkService {
    void saveOrUpdateRestaurant(RestaurantElkModel restaurantElkModel);

    void deleteRestaurant(String id);

    Page<UserSearchResponse> searchRestaurant(BoolQuery boolQuery,
                                        List<SortOptions> sortOptions,
                                        PageRequest pageRequest) throws IOException;
}
