package com.github.moruke.wall.identity.authentication.enums;

import lombok.Getter;

public enum CredentialTypeEnum {

    PASSWORD((byte) 0, "password"),
    MOCK_TEST((byte) 1, "mock_test"),

    ;

    @Getter
    private final Byte code;
    @Getter
    private final String name;

    CredentialTypeEnum(Byte code, String name) {
        this.code = code;
        this.name = name;
    }


}
