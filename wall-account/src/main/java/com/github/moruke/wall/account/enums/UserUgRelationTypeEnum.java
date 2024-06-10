package com.github.moruke.wall.account.enums;

import lombok.Getter;

public enum UserUgRelationTypeEnum {
    DEFAULT((byte) 0, "default"),
    ;

    @Getter
    private final byte code;
    @Getter
    private final String name;

    UserUgRelationTypeEnum(byte code, String name) {
        this.code = code;
        this.name = name;
    }

    public static UserUgRelationTypeEnum getByCode(byte code) {
        for (UserUgRelationTypeEnum value : values()) {
            if (value.code == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }
}
