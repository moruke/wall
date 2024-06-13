package com.github.moruke.wall.account.enums;

import lombok.Getter;

public enum UserUgRelationStatusEnum {
    DEFAULT((byte) 0, "default"),
    ;

    @Getter
    private final byte code;
    @Getter
    private final String name;

    UserUgRelationStatusEnum(byte code, String name) {
        this.code = code;
        this.name = name;
    }

    public static UserUgRelationStatusEnum getByCode(byte code) {
        for (UserUgRelationStatusEnum value : values()) {
            if (value.code == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }
}
