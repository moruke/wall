package com.github.moruke.wall.account.enums;

import lombok.Getter;

/**
 * @author 狄杰
 * @date 2024/6/12
 * @description
 */

public enum OrgPropTypeEnum {
    DEFAULT((byte) 0, "default"),

    ;

    @Getter
    private final byte code;
    @Getter
    private final String name;

    OrgPropTypeEnum(byte code, String name) {
        this.code = code;
        this.name = name;
    }

    public static OrgPropTypeEnum get(byte code) {
        for (OrgPropTypeEnum value : values()) {
            if (value.code == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unknown OrgPropTypeEnum code: " + code);
    }
}
