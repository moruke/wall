package com.github.moruke.wall.auth.enums.permission;

import lombok.Getter;

public enum ObjectStatusEnum {
    DEFAULT("default", (byte) 0),

    ;
    @Getter
    private final String name;
    @Getter
    private final byte code;

    ObjectStatusEnum(String name, byte code) {
        this.name = name;
        this.code = code;
    }

    public static ObjectStatusEnum find(byte code) {
        for (ObjectStatusEnum value : values()) {
            if (value.code == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("unknown code: " + code);
    }
}
