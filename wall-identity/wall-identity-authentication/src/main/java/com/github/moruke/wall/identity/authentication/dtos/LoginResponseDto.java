package com.github.moruke.wall.identity.authentication.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

@Data
public class LoginResponseDto {
    protected String subjectId;
    protected String token;
    protected String refreshToken;
    protected String tokenType;
    protected Long expiresIn;
    protected Long autHisId;
    protected String scope;
    protected String jti;
    protected String clientId;
    protected String state;
    protected String redirectUri;
    protected String code;
    protected String sessionCode;
    protected Date createTime = new Date();

    protected Long rootOrgId;
    protected Long userId;
}

