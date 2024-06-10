package com.github.moruke.wall.account.enums;

import lombok.Getter;

import java.util.Objects;

public enum OrgTypeEnum {
    DEFAULT((byte) 0, "Default"),
    ;

    @Getter
    private Byte code;
    @Getter
    private String name;

    OrgTypeEnum(Byte code, String name) {
        this.code = code;
        this.name = name;
    }

    public static OrgTypeEnum find(Byte type) {
        for (OrgTypeEnum value : values()) {
            if (Objects.equals(value.getCode(), type)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unknown OrgTypeEnum code: " + type);
    }
}
