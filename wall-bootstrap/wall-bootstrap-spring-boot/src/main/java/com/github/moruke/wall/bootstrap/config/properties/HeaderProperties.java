package com.github.moruke.wall.bootstrap.config.properties;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConditionalOnProperty(value = "wall.token.returnType", havingValue = "header")
@ConfigurationProperties("wall.token.token")
public class HeaderProperties extends TokenReturnProp {
    private String tokenHeaderName;
}
