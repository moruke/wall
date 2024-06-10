package com.github.moruke.wall.account.enums;

import lombok.Getter;

import java.util.Objects;

public enum UserGroupStatusEnum {
    DEFAULT((byte) 0, "default"),
    ;

    @Getter
    private Byte code;
    @Getter
    private String name;

    UserGroupStatusEnum(Byte code, String name) {
        this.code = code;
        this.name = name;
    }

    public static UserGroupStatusEnum find(Byte type) {
        for (UserGroupStatusEnum value : values()) {
            if (Objects.equals(value.getCode(), type)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unknown UserGroupStatusEnum code: " + type);
    }
}
