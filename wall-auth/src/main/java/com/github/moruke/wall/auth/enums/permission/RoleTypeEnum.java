package com.github.moruke.wall.auth.enums.permission;

import lombok.Getter;

public enum RoleTypeEnum {
    SYSTEM("系统角色", (byte) 0),
    CUSTOM("自定义角色", (byte) 1),
    OTHER("其他", (byte) 100),
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
