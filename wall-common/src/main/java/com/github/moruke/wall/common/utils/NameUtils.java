package com.github.moruke.wall.common.utils;

import com.github.moruke.wall.common.enums.NameRuleEnum;

import java.util.List;

public class NameUtils {

    public static final int NAME_LENGTH = 20;

    public static boolean checkLength(String name) {
        return name.length() <= NAME_LENGTH;
    }

    public static boolean checkLength(String name, int length) {
        return name.length() <= length;
    }

    public static boolean checkRule(String name, List<String> rules) {
        for (String rule : rules) {
            if (NameRuleEnum.valueOf(rule).check(name)) {
                return false;
            }
        }

        return true;
    }

}
