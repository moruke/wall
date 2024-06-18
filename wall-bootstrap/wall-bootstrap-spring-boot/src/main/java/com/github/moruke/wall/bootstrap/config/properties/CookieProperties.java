package com.github.moruke.wall.bootstrap.config.properties;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConditionalOnProperty(value = "wall.token.returnType", havingValue = "cookie")
@ConfigurationProperties("wall.token.cookie")
public class CookieProperties extends TokenReturnProp {
    private String path;

    private String name;

    private int maxAge;

    private String domain;

    private boolean secure;

    private boolean httpOnly;
}
