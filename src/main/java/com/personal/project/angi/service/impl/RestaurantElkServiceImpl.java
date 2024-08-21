package com.personal.project.angi.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.personal.project.angi.mapping.RestaurantMapper;
import com.personal.project.angi.model.basemodel.UserElkBaseModel;
import com.personal.project.angi.model.dto.response.RestaurantSearchResponse;
import com.personal.project.angi.model.dto.response.UserSearchResponse;
import com.personal.project.angi.model.enity.RestaurantElkModel;
import com.personal.project.angi.model.enity.UserElkModel;
import com.personal.project.angi.repository.elk.RestaurantElkRepository;
import com.personal.project.angi.service.RestaurantElkService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
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
    public Page<RestaurantSearchResponse> searchRestaurant(BoolQuery boolQuery, List<SortOptions> sortOptions, PageRequest pageRequest) throws IOException {
        SearchRequest searchRequest = new SearchRequest.Builder()
                .index(INDEX_NAME)
                .query(boolQuery._toQuery())
                .from(pageRequest.getPageNumber() * pageRequest.getPageSize())
                .size(pageRequest.getPageSize())
                .sort(sortOptions)
                .build();

        SearchResponse<RestaurantElkModel> response = elasticsearchClient.search(searchRequest, RestaurantElkModel.class);


        List<RestaurantElkModel> restaurants = response.hits().hits().stream()
                .map(Hit::source)
                .toList();

        List<RestaurantSearchResponse> restaurantResponse = new ArrayList<>();
        for (RestaurantElkModel user : restaurants) {
            restaurantResponse.add(restaurantMapper.toRestaurantSearchResponse(user));
        }

        long totalHits = response.hits().total().value();

        return new PageImpl<>(restaurantResponse, pageRequest, totalHits);
    }
}
