package com.personal.project.angi.mapping;

import com.personal.project.angi.model.dto.request.UserRegisterRequest;
import com.personal.project.angi.model.dto.response.UserInfoResponse;
import com.personal.project.angi.model.enity.UserElkModel;
import com.personal.project.angi.model.enity.UserInfoModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Value;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Value("${avatar.base-url}")
    String avatarBaseUrl = "";

    @Mapping(target = "points", constant = "0")
    @Mapping(target = "avatarLink", constant = avatarBaseUrl)
    UserInfoModel toUserInfoModel(UserRegisterRequest userRegisterRequest);
    UserElkModel toUserElkModel(UserInfoModel userInfoModel);
    @Mapping(target = "joinDate", source = "createdAt")
    UserInfoResponse toUserInfoResponse(UserInfoModel userInfoModel);
}
