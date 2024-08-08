package com.personal.project.angi.exception;

public class UserDoesHavePermission extends RuntimeException {
    public UserDoesHavePermission(String message) {
        super(message);
    }
}
