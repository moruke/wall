package com.github.moruke.wall.identity.authentication.enums;

import lombok.Getter;

public enum LoginStatusEnum {

    SUCCESS((byte) 0, "success"),
    PROCESSING((byte) 1, "processing"),
    FAILURE((byte) 2, "failure"),
    ;
    @Getter
    private final Byte code;
    @Getter
    private final String name;

    LoginStatusEnum(Byte code, String name) {
        this.code = code;
        this.name = name;
    }

    public static LoginStatusEnum find(Byte status) {
        for (LoginStatusEnum value : values()) {
            if (value.code.equals(status)) {
                return value;
            }
        }
        throw new IllegalArgumentException("unknown status: " + status);
    }

    public boolean isSuccess() {
        return this == SUCCESS;
    }
}
