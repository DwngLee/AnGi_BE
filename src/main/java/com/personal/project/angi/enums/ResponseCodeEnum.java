package com.personal.project.angi.enums;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
public enum ResponseCodeEnum {
    //CREATE USER
    USER1200("USER1200", "User created successfully"),
    USER0200("USER0200", "Username already exists"),
    USER0201("USER0201", "Cannot save user"),
    USER0202("USER0202", "Create user failed"),

    //GET USER
    GETUSER1200("GETUSER1200", "Get user successfully"),
    GETUSER0200("GETUSER0200", "User not found"),
    GETUSER0201("GETUSER0201", "Cannot get user"),
    GETUSER0202("GETUSER0202", "Get user failed"),

    //UPDATE USER
    UPDATEUSER1200("UPDATEUSER1200", "Update user successfully"),
    UPDATEUSER0200("UPDATEUSER0200", "Get user update failed"),

    //GENERAL
    EXCEPTION("EXCEPTION", "Uncatagory Exception"),
    EXCEPTION0400("EXCEPTION0400", "Bad request"), // Bad request
    EXCEPTION0404("EXCEPTION0404", "Not found"), // Not found
    EXCEPTION0504("EXCEPTION0504", "Missing servlet request parameter"), //
    EXCEPTION0505("EXCEPTION0505", "Access Denied/Not have permission"); //

    private final String code, message;

    ResponseCodeEnum(String code, String message) {

        this.code = code;
        this.message = message;
    }


}
