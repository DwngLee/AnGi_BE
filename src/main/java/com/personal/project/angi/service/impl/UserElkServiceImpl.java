package com.personal.project.angi.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.personal.project.angi.mapping.UserMapper;
import com.personal.project.angi.model.enity.UserElkModel;
import com.personal.project.angi.model.enity.UserInfoModel;
import com.personal.project.angi.repository.elk.UserElkRepository;
import com.personal.project.angi.service.UserElkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserElkServiceImpl implements UserElkService {
    private final String INDEX_NAME = "userInfo";

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
}
