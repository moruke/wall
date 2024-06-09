package com.github.moruke.wall.auth.enums.permission;

import lombok.Getter;

public enum PermissionStatusEnum {
    DEFAULT("default", (byte) 0),
    ;
    @Getter
    private final String name;
    @Getter
    private final byte code;

    PermissionStatusEnum(String name, byte code) {
        this.name = name;
        this.code = code;
    }

    public static PermissionStatusEnum find(Byte status) {
        for (PermissionStatusEnum value : values()) {
            if (value.code == status) {
                return value;
            }
        }
        throw new IllegalArgumentException("unknown status: " + status);
    }
}
