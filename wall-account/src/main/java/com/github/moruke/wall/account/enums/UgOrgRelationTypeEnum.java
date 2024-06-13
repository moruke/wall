package com.github.moruke.wall.account.enums;

import lombok.Getter;

public enum UgOrgRelationTypeEnum {
    DEFAULT((byte) 0, "default"),
    ;

    @Getter
    private final byte code;
    @Getter
    private final String name;

    UgOrgRelationTypeEnum(byte code, String name) {
        this.code = code;
        this.name = name;
    }

    public static UgOrgRelationTypeEnum getByCode(byte code) {
        for (UgOrgRelationTypeEnum value : values()) {
            if (value.code == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }
}
