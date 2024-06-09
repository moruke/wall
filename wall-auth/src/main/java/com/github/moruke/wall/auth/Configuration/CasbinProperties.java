package com.github.moruke.wall.auth.Configuration;

import com.github.moruke.wall.auth.enums.casbin.AdapterTypeEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("wall.plugin.auth.component.casbin")
public class CasbinProperties {
    private String modelPath;

    private AdapterTypeEnum adapterType = AdapterTypeEnum.MYBATIS;
}
