package com.github.moruke.wall.bootstrap.config.properties;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConditionalOnProperty(value = "wall.credential.type", havingValue = "password")
@ConfigurationProperties("wall.credential.password")
public class PasswordProp {
    private String algorithm;

    private int saltLength;

    private int iterations;

    private int keyLength;
}
