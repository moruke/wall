package com.github.moruke.wall.auth.enums.permission;

import lombok.Getter;

public enum RoleRelationTypeEnum {

    DEFAULT("default", (byte) 0),

    ;
    @Getter
    private final String name;
    @Getter
    private final byte code;

    RoleRelationTypeEnum(String name, byte code) {
        this.name = name;
        this.code = code;
    }

    public static RoleRelationTypeEnum find(Byte type) {
        for (RoleRelationTypeEnum value : values()) {
            if (value.code == type) {
                return value;
            }
        }
        throw new IllegalArgumentException("unknown type: " + type);
    }
}
