package com.personal.project.angi.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import com.personal.project.angi.mapping.RestaurantMapper;
import com.personal.project.angi.model.dto.response.UserSearchResponse;
import com.personal.project.angi.model.enity.RestaurantElkModel;
import com.personal.project.angi.repository.elk.RestaurantElkRepository;
import com.personal.project.angi.service.RestaurantElkService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantElkServiceImpl implements RestaurantElkService {
    private final String INDEX_NAME = "restaurant";

    private final ElasticsearchClient elasticsearchClient;
    private final RestaurantElkRepository restaurantElkRepository;
    private final RestaurantMapper restaurantMapper;

    @Override
    public void saveOrUpdateRestaurant(RestaurantElkModel restaurantModel) {
        restaurantElkRepository.save(restaurantModel);
    }

    @Override
    public void deleteRestaurant(String id) {

    }

    @Override
    public Page<UserSearchResponse> searchRestaurant(BoolQuery boolQuery, List<SortOptions> sortOptions, PageRequest pageRequest) throws IOException {
        return null;
    }
}
