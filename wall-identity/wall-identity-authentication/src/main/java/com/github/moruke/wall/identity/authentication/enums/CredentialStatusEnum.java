package com.github.moruke.wall.identity.authentication.enums;

public enum CredentialStatusEnum {

    NORMAL((byte) 0, "normal"),

    INEFFECTIVE((byte) 1, "ineffective"),
    ;

    private final Byte code;
    private final String name;

    CredentialStatusEnum(Byte code, String name) {
        this.code = code;
        this.name = name;
    }

    public Byte getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}
