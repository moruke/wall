package com.github.moruke.wall.identity.authentication.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class LoginResponseDto {
    protected String subjectId;
    protected String token;
    protected String refreshToken;
    protected String tokenType;
    protected Byte status;
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
    protected String redirectUrl;
    protected List<String> twoFactors;
    protected Long rootOrgId;
    protected Long userId;
}

