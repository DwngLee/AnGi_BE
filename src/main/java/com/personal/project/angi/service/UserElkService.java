package com.personal.project.angi.service;

import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import com.personal.project.angi.model.dto.response.UserInfoResponse;
import com.personal.project.angi.model.dto.response.UserSearchResponse;
import com.personal.project.angi.model.enity.UserInfoModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.io.IOException;
import java.util.List;

public interface UserElkService {
    void saveOrUpdateUser(UserInfoModel userInfoModel);
    void deleteUser(String id);
    Page<UserSearchResponse> searchUser(BoolQuery boolQuery,
                                        List<SortOptions> sortOptions,
                                        PageRequest pageRequest) throws IOException;

}
