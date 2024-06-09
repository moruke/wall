package com.github.moruke.wall.auth.enums.permission;

import lombok.Getter;

public enum ActionStatusEnum {
    DEFAULT("default", (byte) 0),

    ;

    @Getter
    private final String name;
    @Getter
    private final byte code;

    ActionStatusEnum(String name, byte code) {
        this.name = name;
        this.code = code;
    }

    public static ActionStatusEnum find(Byte status) {
        for (ActionStatusEnum value : values()) {
            if (value.code == status) {
                return value;
            }
        }
        throw new IllegalArgumentException("unknown status: " + status);
    }
}
