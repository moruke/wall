package com.github.moruke.wall.identity.authentication.service;

import com.github.moruke.wall.identity.authentication.dtos.CredentialMetaDto;

public interface ICredential {
    String name();

    String data(String salt, CredentialMetaDto meta, String original);

    String random();
}
