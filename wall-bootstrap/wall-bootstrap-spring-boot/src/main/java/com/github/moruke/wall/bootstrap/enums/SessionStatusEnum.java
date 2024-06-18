package com.github.moruke.wall.bootstrap.enums;

import lombok.Getter;

public enum SessionStatusEnum {
    PROCESSING((byte) -1, "processing"),
    NORMAL((byte) 0, "normal"),
    EXPIRED((byte) 1, "expired"),
    INVALID((byte) 2, "invalid"),

    ;

    @Getter
    private final byte code;
    @Getter
    private final String name;

    SessionStatusEnum(byte code, String name) {
        this.code = code;
        this.name = name;
    }

    public static SessionStatusEnum find(Byte status) {
        for (SessionStatusEnum value : values()) {
            if (value.getCode() == status) {
                return value;
            }
        }
        throw new IllegalArgumentException("invalid status");
    }

    public boolean valid() {
        return this == NORMAL;
    }
}
