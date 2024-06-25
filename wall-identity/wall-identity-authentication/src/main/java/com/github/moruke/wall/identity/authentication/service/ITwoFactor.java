package com.github.moruke.wall.identity.authentication.service;

import com.github.moruke.wall.identity.authentication.dtos.TowFactorDto;

public interface ITwoFactor {
    String name();

    boolean verify(TowFactorDto dto);
}
