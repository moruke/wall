package com.github.moruke.wall.identity.authentication.dtos;

import com.github.moruke.wall.identity.authentication.enums.CredentialTypeEnum;
import com.github.moruke.wall.identity.authentication.enums.TwoFactorTypeEnum;
import lombok.Data;

@Data
public class LoginRequestDto {
    protected String rootOrgName;
    protected String username;
    protected String credentialData;
    protected CredentialTypeEnum type;
    protected Boolean rememberMe = false;
    protected String code;
    protected String sessionCode;
    protected String clientId;
    protected String redirectUri;
    protected String state;
    protected String scope;
    protected String refreshToken;
    protected String grantType;
    protected String responseType;
    protected String socialType;
    protected String socialToken;
    protected String socialOpenId;

    protected Long rootOrgId;
    protected Long userId;
}
