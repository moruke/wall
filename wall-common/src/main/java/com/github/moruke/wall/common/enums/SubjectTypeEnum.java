package com.github.moruke.wall.common.enums;

import lombok.Getter;

import static com.github.moruke.wall.common.constant.CommonConstant.UNDERLINE;

public enum SubjectTypeEnum {
    USER(1, "user", "u"),
    USER_GROUP(2, "user_group", "ug"),
    ROLE(3, "role", "r"),

    ;
    @Getter
    private final int code;
    @Getter
    private final String name;
    @Getter
    private final String prefix;

    SubjectTypeEnum(int code, String name, String prefix) {
        this.code = code;
        this.name = name;
        this.prefix = prefix;
    }

    public static SubjectTypeEnum find(int code) {
        for (SubjectTypeEnum value : values()) {
            if (value.code == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("unknown code: " + code);
    }

    public static SubjectTypeEnum extra(String id) {
        for (SubjectTypeEnum value : values()) {
            if (id.startsWith(value.getPrefix())) {
                return value;
            }
        }
        throw new IllegalArgumentException("unknown id: " + id);
    }

    public String getId(Long id) {
        return this.getPrefix() + UNDERLINE + id;
    }

    public Long getId(String id) {
        return Long.parseLong(id.split(UNDERLINE)[1]);
    }

}
