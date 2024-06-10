package com.github.moruke.wall.account.enums;

import lombok.Getter;

/**
 * @author 狄杰
 * @date 2024/6/13
 * @description
 */

public enum SourceTypeEnum {
    DEFAULT((byte) 0, "default"),

    ;

    @Getter
    private final byte code;
    @Getter
    private final String desc;

    SourceTypeEnum(byte code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static SourceTypeEnum find(byte code) {
        for (SourceTypeEnum value : values()) {
            if (value.code == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("invalid code: " + code);
    }
}
