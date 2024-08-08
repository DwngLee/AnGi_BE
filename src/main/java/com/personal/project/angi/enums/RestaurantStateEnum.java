package com.personal.project.angi.enums;

public enum RestaurantStateEnum {
    PENDING (1),
    DENINED (2),
    ACTIVE (3),
    CLOSED (4),
    BANNED (5);

    public final int value;

    RestaurantStateEnum(int i){
        value = i;
    }
}
