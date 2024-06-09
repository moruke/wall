package com.github.moruke.wall.auth.enums.permission;

import lombok.Getter;

public enum ObjectTypeEnum {
    DEFAULT("default", (byte) 0),

    ;
    @Getter
    private final String name;
    @Getter
    private final byte code;

    ObjectTypeEnum(String name, byte code) {
        this.name = name;
        this.code = code;
    }

    public static ObjectTypeEnum find(Byte type) {
        for (ObjectTypeEnum value : values()) {
            if (value.code == type) {
                return value;
            }
        }
        throw new IllegalArgumentException("unknown type: " + type);
    }
}
