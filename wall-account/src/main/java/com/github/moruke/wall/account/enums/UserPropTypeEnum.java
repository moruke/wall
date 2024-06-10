package com.github.moruke.wall.account.enums;

import lombok.Getter;

public enum UserPropTypeEnum {
    DEFAULT((byte) 0, "default"),

    ;

    @Getter
    private final byte code;
    @Getter
    private final String name;

    UserPropTypeEnum(byte code, String name) {
        this.code = code;
        this.name = name;
    }

    public static UserPropTypeEnum get(byte code) {
        for (UserPropTypeEnum value : values()) {
            if (value.code == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unknown UserPropTypeEnum code: " + code);
    }
}
