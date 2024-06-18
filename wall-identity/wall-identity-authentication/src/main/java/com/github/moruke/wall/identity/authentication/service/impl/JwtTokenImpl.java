package com.github.moruke.wall.identity.authentication.service.impl;

import com.github.moruke.wall.common.enums.SubjectTypeEnum;
import com.github.moruke.wall.identity.authentication.dtos.TokenInfoDto;
import com.github.moruke.wall.identity.authentication.dtos.UserTokenInfoDto;
import com.github.moruke.wall.identity.authentication.service.IToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.crypto.SecretKey;
import java.lang.reflect.Field;
import java.security.Key;
import java.util.Date;

import static com.github.moruke.wall.common.constant.CommonConstant.SUBJECT_TYPE;

@Component
public class JwtTokenImpl implements IToken {

    // todo add token scope

    @Value("${wall.token.expiration:86400000}")
    private Long expiration;

    @Value("${wall.token.refreshExpiration:86400000}")
    private Long refreshExpiration;

    @Resource
    private SecretKey key;

    @Resource
    private SecureDigestAlgorithm<Key, ?> algorithm;

    @Override
    public String generateToken(TokenInfoDto dto) {
        return Jwts.builder()
                .issuedAt(new Date())
                .subject(dto.getType().getName())
                .claims(dto.toMap())
                .signWith(key, algorithm)
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .compact();
    }

    @Override
    public String generateRefreshToken(TokenInfoDto dto) {
        return Jwts.builder()
                .issuedAt(new Date())
                .subject(dto.getType().getName())
                .claims(dto.toMap())
                .signWith(key, algorithm)
                .expiration(new Date(System.currentTimeMillis() + refreshExpiration))
                .compact();
    }

    @Override
    public TokenInfoDto parseToken(String token) {
        final JwtParser parser = Jwts.parser()
                .verifyWith(key)
                .build();
        final Claims payload = parser.parseSignedClaims(token).getPayload();
        final String type = payload.get(SUBJECT_TYPE).toString();
        final SubjectTypeEnum typeEnum = SubjectTypeEnum.find(type);
        return getFromClaims(typeEnum, payload);
    }

    @Override
    public boolean validateToken(String token) {
        return Jwts.parser().verifyWith(key).build().isSigned(token);
    }

    @Override
    public boolean validateRefreshToken(String token) {
        return validateToken(token);
    }

    private TokenInfoDto getFromClaims(SubjectTypeEnum type, Claims claims) {
        switch (type) {
            case USER:
                for (Field field : UserTokenInfoDto.class.getDeclaredFields()) {
                    field.setAccessible(true);
                    try {
                        field.set(field.getName(), claims.get(field.getName()));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            default:
                throw new IllegalArgumentException("Unknown type: " + type);
        }

    }
}
