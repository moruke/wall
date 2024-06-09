package com.github.moruke.wall.demo.auth;

import com.github.moruke.wall.common.dto.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    private Result<String> exceptionHandler(Exception e) {
        logError(e);
        return Result.fail(500, e.getMessage());
    }

    private void logError(Throwable throwable) {
        log.error(throwable.getMessage(), throwable);
    }

}
