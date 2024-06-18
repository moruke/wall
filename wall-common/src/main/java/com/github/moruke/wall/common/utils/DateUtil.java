package com.github.moruke.wall.common.utils;

import java.util.Date;

public class DateUtil extends org.apache.commons.lang3.time.DateUtils {
    public static Date maxDate() {
        return new Date(253402271999000L);
    }
}
