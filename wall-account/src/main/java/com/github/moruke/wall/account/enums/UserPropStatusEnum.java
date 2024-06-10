package com.github.moruke.wall.account.enums;

import lombok.Getter;

public enum UserPropStatusEnum {
    DEFAULT((byte) 0, "default"),

    ;

    @Getter
    private final byte code;
    @Getter
    private final String name;

    UserPropStatusEnum(byte code, String name) {
        this.code = code;
        this.name = name;
    }

    public static UserPropStatusEnum get(byte code) {
        for (UserPropStatusEnum value : values()) {
            if (value.code == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unknown UserPropStatusEnum code: " + code);
    }
}
