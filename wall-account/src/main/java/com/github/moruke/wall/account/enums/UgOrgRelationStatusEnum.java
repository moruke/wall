package com.github.moruke.wall.account.enums;

import lombok.Getter;

public enum UgOrgRelationStatusEnum {
    DEFAULT((byte) 0, "default"),
    ;

    @Getter
    private final byte code;
    @Getter
    private final String name;

    UgOrgRelationStatusEnum(byte code, String name) {
        this.code = code;
        this.name = name;
    }

    public static UgOrgRelationStatusEnum getByCode(byte code) {
        for (UgOrgRelationStatusEnum value : values()) {
            if (value.code == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }
}
