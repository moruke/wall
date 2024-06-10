package com.github.moruke.wall.common.enums;

public enum NameRuleEnum {
    NO_SPECIAL_CHARACTER,
    NO_SPACE,
    NO_DIGIT,
    NO_UPPERCASE,
    NO_LOWERCASE,
    NO_CHINESE,
    ;

    public boolean check(String name) {
        switch (this) {
            case NO_SPECIAL_CHARACTER:
                return !name.matches("^[a-zA-Z0-9\\u4e00-\\u9fa5]+$");
            case NO_SPACE:
                return !name.matches("^[^\\s]+$");
            case NO_DIGIT:
                return !name.matches("^[^0-9]+$");
            case NO_UPPERCASE:
                return !name.matches("^[^A-Z]+$");
            case NO_LOWERCASE:
                return !name.matches("^[^a-z]+$");
            case NO_CHINESE:
                return !name.matches("^[^\\u4e00-\\u9fa5]+$");
            default:
                return false;
        }
    }
}
