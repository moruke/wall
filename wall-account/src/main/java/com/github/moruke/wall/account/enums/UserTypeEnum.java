package com.github.moruke.wall.account.enums;

import lombok.Getter;

import java.util.Objects;

public enum UserTypeEnum {
    DEFAULT((byte) 0, "Default"),
    ;

    @Getter
    private Byte code;
    @Getter
    private String name;

    UserTypeEnum(Byte code, String name) {
        this.code = code;
        this.name = name;
    }

    public static UserTypeEnum find(Byte type) {
        for (UserTypeEnum value : values()) {
            if (Objects.equals(value.getCode(), type)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unknown UserTypeEnum code: " + type);
    }
}
