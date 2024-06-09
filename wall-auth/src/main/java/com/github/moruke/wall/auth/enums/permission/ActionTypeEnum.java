package com.github.moruke.wall.auth.enums.permission;

import lombok.Getter;

public enum ActionTypeEnum {
    DEFAULT("default", (byte) 0),

    ;
    @Getter
    private final String name;
    @Getter
    private final byte code;

    ActionTypeEnum(String name, byte code) {
        this.name = name;
        this.code = code;
    }

    public static ActionTypeEnum find(Byte type) {
        for (ActionTypeEnum value : values()) {
            if (value.code == type) {
                return value;
            }
        }
        throw new IllegalArgumentException("unknown type: " + type);
    }
}
