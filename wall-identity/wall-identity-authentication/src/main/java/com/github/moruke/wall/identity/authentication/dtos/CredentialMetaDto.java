package com.github.moruke.wall.identity.authentication.dtos;

import lombok.Data;

@Data
public class CredentialMetaDto {
    private String algorithm;
    private int saltLength;
    private int iterations;
    private int keyLength;
}
