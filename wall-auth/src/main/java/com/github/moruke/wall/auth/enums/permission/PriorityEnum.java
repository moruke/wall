package com.github.moruke.wall.auth.enums.permission;

public enum PriorityEnum {
    LOWEST(0, "lowest"),
    LOW(1, "low"),
    MEDIUM(2, "medium"),
    HIGH(3, "high"),
    HIGHEST(100, "highest");

    private final int code;
    private final String desc;

    PriorityEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static PriorityEnum find(int code) {
        for (PriorityEnum value : values()) {
            if (value.code == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("invalid code");
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
