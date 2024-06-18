package com.github.moruke.wall.auth.enums.permission;

import lombok.Getter;

public enum RoleTypeEnum {
    SYSTEM("system role", (byte) 0),
    CUSTOM("custom role", (byte) 1),
    OTHER("other", (byte) 100),
    ;
    @Getter
    private final String name;
    @Getter
    private final byte code;

    RoleTypeEnum(String name, byte code) {
        this.name = name;
        this.code = code;
    }

    public static RoleTypeEnum find(Byte type) {
        for (RoleTypeEnum value : values()) {
            if (value.code == type) {
                return value;
            }
        }
        throw new IllegalArgumentException("unknown type: " + type);
    }
}
