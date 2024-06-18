package com.github.moruke.wall.identity.authentication.service;

import com.github.moruke.wall.identity.authentication.dtos.LoginRequestDto;
import com.github.moruke.wall.identity.authentication.enums.CredentialTypeEnum;

public interface IAutProcessor {

    String name();

    CredentialTypeEnum type();

    void authenticate(LoginRequestDto req);
}
