package com.github.moruke.wall.auth.Configuration;

import com.github.moruke.wall.auth.core.impl.casbin.MybatisAdapter;
import com.github.moruke.wall.auth.dao.mapper.CasbinPolicyMapper;
import com.github.moruke.wall.auth.dao.mapper.PermissionMapper;
import org.casbin.jcasbin.main.Enforcer;
import org.casbin.jcasbin.main.SyncedEnforcer;
import org.casbin.jcasbin.persist.Adapter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.io.IOException;

import static java.util.Objects.requireNonNull;

@Configuration
@EnableConfigurationProperties({CasbinProperties.class})
public class RbacConfiguration {

    @Resource
    private CasbinPolicyMapper casbinPolicyMapper;

    @Resource
    private PermissionMapper permissionMapper;

    @Bean
    @ConditionalOnProperty(prefix = "wall.plugin.auth.component", name = "type", havingValue = "casbin")
    Enforcer rbacEnforcer(CasbinProperties casbinProperties) throws IOException {

        Adapter adapter = getAdapter(casbinProperties);

        final String modelPath = requireNonNull(getClass().getClassLoader().getResource(casbinProperties.getModelPath())).getFile();
        return adapter == null ?
                new SyncedEnforcer(modelPath) :
                new SyncedEnforcer(modelPath, adapter);
    }

    private Adapter getAdapter(CasbinProperties casbinProperties) throws IOException {
        switch (casbinProperties.getAdapterType()) {
            case NONE:
                return null;
            case MYBATIS:
                return new MybatisAdapter(casbinPolicyMapper, permissionMapper, casbinProperties);
            default:
                throw new UnsupportedOperationException();
        }
    }
}
