package com.personal.project.angi.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.personal.project.angi.mapping.UserMapper;
import com.personal.project.angi.model.basemodel.UserElkBaseModel;
import com.personal.project.angi.model.dto.response.UserInfoResponse;
import com.personal.project.angi.model.dto.response.UserSearchResponse;
import com.personal.project.angi.model.enity.UserElkModel;
import com.personal.project.angi.model.enity.UserInfoModel;
import com.personal.project.angi.repository.elk.UserElkRepository;
import com.personal.project.angi.service.UserElkService;
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
public class UserElkServiceImpl implements UserElkService {
    private final String INDEX_NAME = "user_info";

    private final ElasticsearchClient elasticsearchClient;
    private final UserElkRepository userElkRepository;
    private final UserMapper userMapper;

    @Override
    public void saveOrUpdateUser(UserInfoModel userInfoModel) {
        userElkRepository.save(userMapper.toUserElkModel(userInfoModel));
    }

    @Override
    public void deleteUser(String id) {

    }

    @Override
    public Page<UserSearchResponse> searchUser(BoolQuery boolQuery, List<SortOptions> sortOptions, PageRequest pageRequest) throws IOException {
        SearchRequest searchRequest = new SearchRequest.Builder()
                .index(INDEX_NAME)
                .query(boolQuery._toQuery())
                .from(pageRequest.getPageNumber() * pageRequest.getPageSize())
                .size(pageRequest.getPageSize())
                .sort(sortOptions)
                .build();

        SearchResponse<UserElkModel> response = elasticsearchClient.search(searchRequest, UserElkModel.class);


        List<UserElkModel> users = response.hits().hits().stream()
                .map(Hit::source)
                .toList();

        List<UserSearchResponse> userResponses = new ArrayList<>();
        for (UserElkBaseModel user : users) {
            userResponses.add(userMapper.toUserSearchResponse(user));
        }

        long totalHits = response.hits().total().value();

        return new PageImpl<>(userResponses, pageRequest, totalHits);
    }
}
