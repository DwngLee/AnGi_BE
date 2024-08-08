package com.personal.project.angi.enums;

public enum AccountStateEnum {
    INACTIVE(0),
    ACTIVE(1),
    BANNED(2);

    public final int value;
    AccountStateEnum(int i){
        value = i;
    }
}
