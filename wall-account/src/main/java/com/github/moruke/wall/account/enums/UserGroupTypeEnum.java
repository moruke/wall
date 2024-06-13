package com.github.moruke.wall.account.enums;

import lombok.Getter;

import java.util.Objects;

public enum UserGroupTypeEnum {
    DEFAULT((byte) 0, "Default"),
    ;

    @Getter
    private Byte code;
    @Getter
    private String name;

    UserGroupTypeEnum(Byte code, String name) {
        this.code = code;
        this.name = name;
    }

    public static UserGroupTypeEnum find(Byte type) {
        for (UserGroupTypeEnum value : values()) {
            if (Objects.equals(value.getCode(), type)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unknown UserGroupTypeEnum code: " + type);
    }
}
