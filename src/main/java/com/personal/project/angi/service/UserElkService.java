package com.personal.project.angi.service;

import com.personal.project.angi.model.enity.UserElkModel;
import com.personal.project.angi.model.enity.UserInfoModel;

public interface UserElkService {
    void saveorUpdateUser(UserInfoModel userInfoModel);
    void deleteUser(String id);

}
