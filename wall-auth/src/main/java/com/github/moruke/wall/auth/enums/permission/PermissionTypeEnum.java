package com.github.moruke.wall.auth.enums.permission;

import lombok.Getter;

public enum PermissionTypeEnum {
    DEFAULT("default", (byte) 0),
    ;
    @Getter
    private final String name;
    @Getter
    private final byte code;

    PermissionTypeEnum(String name, byte code) {
        this.name = name;
        this.code = code;
    }

    public static PermissionTypeEnum find(Byte type) {
        for (PermissionTypeEnum value : values()) {
            if (value.code == type) {
                return value;
            }
        }
        throw new IllegalArgumentException("unknown type: " + type);
    }
}
