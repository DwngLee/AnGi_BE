package com.personal.project.angi.service;

import com.personal.project.angi.model.enity.UserInfoModel;

public interface UserElkService {
    void saveOrUpdateUser(UserInfoModel userInfoModel);
    void deleteUser(String id);

}
