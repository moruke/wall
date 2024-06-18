package com.github.moruke.wall.identity.authentication.service;

import com.github.moruke.wall.identity.authentication.dtos.LoginRequestDto;
import com.github.moruke.wall.identity.authentication.dtos.TokenInfoDto;

public interface IToken {
    String generateToken(TokenInfoDto dto);

    String generateRefreshToken(TokenInfoDto dto);

    TokenInfoDto parseToken(String token);

    boolean validateToken(String token);

    boolean validateRefreshToken(String token);
}
