package com.github.moruke.wall.account.enums;

import lombok.Getter;

public enum UserOrgRelationTypeEnum {
    DEFAULT((byte) 0, "default"),
    ;

    @Getter
    private final byte code;
    @Getter
    private final String name;

    UserOrgRelationTypeEnum(byte code, String name) {
        this.code = code;
        this.name = name;
    }

    public static UserOrgRelationTypeEnum getByCode(byte code) {
        for (UserOrgRelationTypeEnum value : values()) {
            if (value.code == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }
}
