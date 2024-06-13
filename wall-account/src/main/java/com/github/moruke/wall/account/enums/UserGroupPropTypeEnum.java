package com.github.moruke.wall.account.enums;

import lombok.Getter;

/**
 * @author 狄杰
 * @date 2024/6/12
 * @description
 */

public enum UserGroupPropTypeEnum {
    DEFAULT((byte) 0, "default"),

    ;

    @Getter
    private final byte code;
    @Getter
    private final String name;

    UserGroupPropTypeEnum(byte code, String name) {
        this.code = code;
        this.name = name;
    }

    public static UserGroupPropTypeEnum get(byte code) {
        for (UserGroupPropTypeEnum value : values()) {
            if (value.code == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unknown UserGroupPropTypeEnum code: " + code);
    }
}
