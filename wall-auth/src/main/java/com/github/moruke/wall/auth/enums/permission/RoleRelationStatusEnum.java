package com.github.moruke.wall.auth.enums.permission;

import lombok.Getter;

public enum RoleRelationStatusEnum {
    DEFAULT("default", (byte) 0),

    ;

    @Getter
    private final String name;
    @Getter
    private final byte code;

    RoleRelationStatusEnum(String name, byte code) {
        this.name = name;
        this.code = code;
    }

    public static RoleRelationStatusEnum find(Byte status) {
        for (RoleRelationStatusEnum value : values()) {
            if (value.code == status) {
                return value;
            }
        }
        throw new IllegalArgumentException("unknown status: " + status);
    }
}
