package com.github.moruke.wall.bootstrap.enums;

public enum TokenReturnTypeEnum {
    COOKIE((byte)0, "cookie"),
    HEADER((byte)1, "header"),
    BODY((byte)2, "body"),
    ;

    private final byte code;
    private final String name;

    TokenReturnTypeEnum(byte code, String name) {
        this.code = code;
        this.name = name;
    }

    public static TokenReturnTypeEnum find(String returnType) {
        for (TokenReturnTypeEnum value : values()) {
            if (value.name.equals(returnType)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unknown returnType: " + returnType);
    }
}
