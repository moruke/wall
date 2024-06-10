package com.github.moruke.wall.account.enums;

import lombok.Getter;

import java.util.Objects;

public enum UserStatusEnum {
    DEFAULT((byte) 0, "default"),
    ;

    @Getter
    private Byte code;
    @Getter
    private String name;

    UserStatusEnum(Byte code, String name) {
        this.code = code;
        this.name = name;
    }

    public static UserStatusEnum find(Byte type) {
        for (UserStatusEnum value : values()) {
            if (Objects.equals(value.getCode(), type)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unknown UserStatusEnum code: " + type);
    }
}
