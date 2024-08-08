package com.personal.project.angi.enums;

public enum UpdateStateEnum {
    DAFT (0),
    PENDING (1),
    DENIED (2),
    ACCEPTED (3);

    public final int value;

    UpdateStateEnum(int i){
        value = i;
    }
}
