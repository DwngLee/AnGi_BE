package com.personal.project.angi.enums;

import lombok.Getter;

@Getter
public enum MessageResponseEnum {
    //Create user
    CREATED_USER_SUCCESS("User created successfully"),
    CREATED_USER_FAILED("Create user failed"),

    //Get user info
    GET_USER_INFO_SUCCESS("Get user info successfully"),
    GET_USER_INFO_FAILED("Get user info failed"),

    //Update user avatar
    UPDATE_USER_AVATAR_SUCCESS("Update user avatar successfully"),
    UPDATE_USER_AVATAR_FAILED("Update user avatar failed"),

    //Search user
    SEARCH_USER_SUCCESS("Search user successfully"),
    SEARCH_USER_FAILED("Search user failed"),

    //Update user info
    UPDATE_USER_INFO_SUCCESS("Update user info successfully"),
    UPDATE_USER_INFO_FAILED("Update user info failed");

    private final String message;
    MessageResponseEnum(String message) {
        this.message = message;
    }
}
