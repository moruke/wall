package com.github.moruke.wall.common.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

    private static ObjectMapper mapper = new ObjectMapper();

    public static <T> T readFromString(String responseBody, Class<T> clazz) {
        try {
            return mapper.readValue(responseBody, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T readFromString(String responseBody, TypeReference<T> type) {
        try {
            return mapper.readValue(responseBody, type);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String writeAsString(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
