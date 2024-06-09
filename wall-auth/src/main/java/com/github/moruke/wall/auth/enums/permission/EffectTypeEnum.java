package com.github.moruke.wall.auth.enums.permission;


import java.util.Objects;

public enum EffectTypeEnum {
    ALLOW,
    DENY,
    ;

    public static EffectTypeEnum find(String result) {
        for (EffectTypeEnum value : values()) {
            if (Objects.equals(value.name(), result)) {
                return value;
            }
        }
        throw new IllegalArgumentException("unknown effect type: " + result);
    }

    public boolean isAllow() {
        return this == ALLOW;
    }
}
