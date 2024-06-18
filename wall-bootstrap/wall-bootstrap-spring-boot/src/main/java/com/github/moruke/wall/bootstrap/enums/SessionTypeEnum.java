package com.github.moruke.wall.bootstrap.enums;

import lombok.Getter;

public enum SessionTypeEnum {
    NORMAL((byte) 0, "normal"),

    ;

    @Getter
    private final byte code;
    @Getter
    private final String name;

    SessionTypeEnum(byte code, String name) {
        this.code = code;
        this.name = name;
    }
}

