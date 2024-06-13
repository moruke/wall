package com.github.moruke.wall.account.enums;

import lombok.Getter;

/**
 * @author 狄杰
 * @date 2024/6/12
 * @description
 */

public enum UserGroupPropStatusEnum {
    DEFAULT((byte) 0, "default"),

    ;

    @Getter
    private final byte code;
    @Getter
    private final String name;

    UserGroupPropStatusEnum(byte code, String name) {
        this.code = code;
        this.name = name;
    }

    public static UserGroupPropStatusEnum get(byte code) {
        for (UserGroupPropStatusEnum value : values()) {
            if (value.code == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unknown UserGroupPropStatusEnum code: " + code);
    }
}
