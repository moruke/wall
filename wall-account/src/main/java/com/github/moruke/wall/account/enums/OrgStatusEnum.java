package com.github.moruke.wall.account.enums;

import lombok.Getter;

import java.util.Objects;

public enum OrgStatusEnum {
    DEFAULT((byte) 0, "default"),
    ;

    @Getter
    private Byte code;
    @Getter
    private String name;

    OrgStatusEnum(Byte code, String name) {
        this.code = code;
        this.name = name;
    }

    public static OrgStatusEnum find(Byte status) {
        for (OrgStatusEnum value : values()) {
            if (Objects.equals(value.getCode(), status)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unknown OrgStatusEnum code: " + status);
    }

    public boolean valid() {
        return this == DEFAULT;
    }
}
