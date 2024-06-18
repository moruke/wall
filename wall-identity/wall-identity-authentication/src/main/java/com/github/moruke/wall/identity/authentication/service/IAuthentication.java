package com.github.moruke.wall.identity.authentication.service;

import com.github.moruke.wall.identity.authentication.dtos.LoginRequestDto;
import com.github.moruke.wall.identity.authentication.dtos.LoginResponseDto;

public interface IAuthentication {

    LoginResponseDto login(LoginRequestDto loginRequestDto);

    LoginResponseDto refreshToken(String refreshToken);

    void logoutAll(String username);

    void logoutOne(String username, String clientId);

    String random(String subjectId);

    LoginResponseDto bind(LoginRequestDto dto);
}
