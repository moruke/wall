package com.github.moruke.wall.bootstrap.config.properties;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConditionalOnProperty(value = "wall.token.type", havingValue = "jwt")
@ConfigurationProperties("wall.token.jwt")
public class JwtProperties extends TokenProperties {
    private String secret;

    private String algorithm;

    private long expiration;
}
