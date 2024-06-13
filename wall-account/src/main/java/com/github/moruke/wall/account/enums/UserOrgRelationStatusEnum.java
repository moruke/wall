package com.github.moruke.wall.account.enums;

import lombok.Getter;

public enum UserOrgRelationStatusEnum {
    DEFAULT((byte) 0, "default"),
    ;

    @Getter
    private final byte code;
    @Getter
    private final String name;

    UserOrgRelationStatusEnum(byte code, String name) {
        this.code = code;
        this.name = name;
    }

    public static UserOrgRelationStatusEnum getByCode(byte code) {
        for (UserOrgRelationStatusEnum value : values()) {
            if (value.code == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }
}
