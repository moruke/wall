package com.github.moruke.wall.bootstrap.config;

import com.github.moruke.wall.bootstrap.config.properties.JwtProperties;
import com.github.moruke.wall.bootstrap.config.properties.TokenProperties;
import com.github.moruke.wall.bootstrap.enums.TokenReturnTypeEnum;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

@Configuration
public class TokenConfig {

    @Value("${wall.token.returnType}")
    private String returnType;

    @Resource
    private TokenProperties tokenProperties;

    @Bean
    public TokenReturnTypeEnum type() {
        return TokenReturnTypeEnum.find(returnType);
    }

    @Bean
    @ConditionalOnProperty(value = "wall.token.type", havingValue = "jwt")
    public SecretKey secretKey() {
        final JwtProperties properties = (JwtProperties) tokenProperties;

        return new SecretKeySpec(properties.getSecret().getBytes(), SignatureAlgorithm.valueOf(properties.getAlgorithm()).getJcaName());
    }

    @Bean
    @ConditionalOnProperty(value = "wall.token.type", havingValue = "jwt")
    public SecureDigestAlgorithm<? extends Key, ?> secureDigestAlgorithm() {
        final JwtProperties properties = (JwtProperties) tokenProperties;
        final String algorithm = properties.getAlgorithm();

        return Jwts.SIG.get().forKey(algorithm);
    }
}
