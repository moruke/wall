package com.github.moruke.wall.common.dto;

import lombok.Data;

import java.io.Serializable;

// Web API response object
@Data
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private int code;
    private String message;
    private T data;
    private long timestamp = System.currentTimeMillis();

    private Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(200, "success", data);
    }

    public static <T> Result<T> success() {
        return new Result<>(200, "success", null);
    }

    public static <T> Result<T> fail(String message) {
        return new Result<>(500, message, null);
    }

    public static <T> Result<T> fail(int code, String message) {
        return new Result<>(code, message, null);
    }
}
