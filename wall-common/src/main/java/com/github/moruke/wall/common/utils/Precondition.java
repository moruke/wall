package com.github.moruke.wall.common.utils;

public class Precondition {

    public static void checkNull(Object obj, String message) {
        if (obj != null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void checkNotNull(Object obj, String message) {
        if (obj == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void checkNotEmpty(String str, String message) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void checkNotEmpty(Object[] array, String message) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void checkNotEmpty(Iterable<?> iterable, String message) {
        if (iterable == null || !iterable.iterator().hasNext()) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void checkArgument(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void checkState(boolean expression, String message) {
        if (!expression) {
            throw new IllegalStateException(message);
        }
    }
}
