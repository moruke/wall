package com.github.moruke.wall.identity.authentication.utils;

import com.github.moruke.wall.identity.authentication.dtos.LoginRequestDto;
import com.github.moruke.wall.identity.authentication.dtos.LoginResponseDto;

public class AuthenticationContext {
    private static ThreadLocal<LoginResponseDto> RESPONSE = new ThreadLocal<>();

    private static ThreadLocal<LoginRequestDto> REQUEST = new ThreadLocal<>();

    public static LoginResponseDto getResponse() {
        return RESPONSE.get();
    }

    public static void setResponse(LoginResponseDto loginResponse) {
        RESPONSE.set(loginResponse);
    }

    public static LoginRequestDto getRequest() {
        return REQUEST.get();
    }

    public static void setRequest(LoginRequestDto loginRequest) {
        REQUEST.set(loginRequest);
    }
}
