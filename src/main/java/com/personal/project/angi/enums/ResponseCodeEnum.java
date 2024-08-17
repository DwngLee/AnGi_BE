package com.personal.project.angi.enums;

import lombok.Getter;

@Getter
public enum ResponseCodeEnum {
    //LOGIN
    LOGIN1200("LOGIN1200", "Login successfully"),
    LOGIN0200("LOGIN0200", "Username or password is incorrect"),
    LOGIN0201("LOGIN0201", "Generate token failed"),

    //LOGOUT
    LOGOUT1200("LOGOUT1200", "Logout successfully"),
    LOGOUT0200("LOGOUT0200", "Logout failed"),
    LOGOUT0201("LOGOUT0201", "Cant find the token"),

    //REFRESH TOKEN
    REFRESHTOKEN1200("REFRESHTOKEN1200", "Refresh token successfully"),
    REFRESHTOKEN0200("REFRESHTOKEN0200", "Refresh token failed"),
    REFRESHTOKEN0201("REFRESHTOKEN0201", "Refresh token is expired or has been revoked"),
    REFRESHTOKEN0202("REFRESHTOKEN0202", "User not found"),
    REFRESHTOKEN0203("REFRESHTOKEN0203", "Generate new token failed"),

    //CREATE USER
    USER1200("USER1200", "User created successfully"),
    USER0200("USER0200", "Username already exists"),
    USER0201("USER0201", "Cannot save user"),
    USER0202("USER0202", "Create user failed with uncategory exception"),

    //GET USER
    GETUSER1200("GETUSER1200", "Get user successfully"),
    GETUSER0200("GETUSER0200", "User not found"),
    GETUSER0201("GETUSER0201", "Cannot get user"),
    GETUSER0202("GETUSER0202", "Get user failed with uncategory exception"),

    //UPDATE USER
    UPDATEUSER1200("UPDATEUSER1200", "Update user successfully"),
    UPDATEUSER0200("UPDATEUSER0200", "Get user update failed"),
    UPDATEUSER0201("UPDATEUSER0201", "Cannot update user"),
    UPDATEUSER0202("UPDATEUSER0202", "Save user update information failed"),
    UPDATEUSER0203("UPDATEUSER0203", "Update user failed with uncategory exception"),

    //UPDATE USER AVATAR
    UPDATEUSERAVATAR1200("UPDATEUSERAVATAR1200", "Update user avatar successfully"),
    UPDATEUSERAVATAR0200("UPDATEUSERAVATAR0200", "Get user avatar update failed"),
    UPDATEUSERAVATAR0201("UPDATEUSERAVATAR0201", "Upload avatar image  to cloud failed"),
    UPDATEUSERAVATAR0202("UPDATEUSERAVATAR0202", "Save avatar image failed"),
    UPDATEUSERAVATAR0203("UPDATEUSERAVATAR0203", "Update user avatar failed with uncategory exception"),

    //SEARCH USER
    SEARCHUSER1200("SEARCHUSER1200", "Search user successfully"),
    SEARCHUSER0200("SEARCHUSER0200", "Search user failed with uncategory exception"),
    SEARCHUSER0201("SEARCHUSER0201", "Search user from ELK failed"),


    //GENERAL
    EXCEPTION("EXCEPTION", "Uncatagory Exception"),
    EXCEPTION0400("EXCEPTION0400", "Bad request"), // Bad request
    EXCEPTION0404("EXCEPTION0404", "Not found"), // Not found
    EXCEPTION0504("EXCEPTION0504", "Missing servlet request parameter"), //
    EXCEPTION0505("EXCEPTION0505", "Access Denied/Not have permission");


    private final String code, message;

    ResponseCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
    }
