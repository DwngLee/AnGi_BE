package com.personal.project.angi.mapping;

import com.personal.project.angi.model.basemodel.UserElkBaseModel;
import com.personal.project.angi.model.basemodel.UserInfoBaseModel;
import com.personal.project.angi.model.dto.request.UserRegisterRequest;
import com.personal.project.angi.model.dto.request.UserUpdateInfoRequest;
import com.personal.project.angi.model.dto.response.UserInfoResponse;
import com.personal.project.angi.model.dto.response.UserRestaurantResponse;
import com.personal.project.angi.model.dto.response.UserSearchResponse;
import com.personal.project.angi.model.enity.UserElkModel;
import com.personal.project.angi.model.enity.UserInfoModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collections;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserMapper {
    String avatarBaseUrl = "https://res.cloudinary.com/daxhouput/image/upload/v1723019192/samples/dessert-on-a-plate.jpg";
    String defaultRole = "ROLE_USER";


    @Mapping(target = "points", constant = "0")
    @Mapping(target = "avatarUrl", constant = avatarBaseUrl)
    @Mapping(target = "roles", expression = "java(getDefaultRoles())")
    UserInfoModel toUserInfoModel(UserRegisterRequest userRegisterRequest);
    UserElkModel toUserElkModel(UserInfoModel userInfoModel);
    @Mapping(target = "joinDate", source = "createdAt")
    UserInfoResponse toUserInfoResponse(UserInfoModel userInfoModel);
    void updateUserInfoModel(@MappingTarget UserInfoModel userInfoModel, UserUpdateInfoRequest userUpdateInfoRequest);
    UserSearchResponse toUserSearchResponse(UserElkBaseModel userElkModel);
    UserRestaurantResponse toUserRestaurantResponse(UserInfoModel userInfoModel);

    // Helper method to provide default roles
    default Set<String> getDefaultRoles() {
        return Collections.singleton(defaultRole);
    }
}


