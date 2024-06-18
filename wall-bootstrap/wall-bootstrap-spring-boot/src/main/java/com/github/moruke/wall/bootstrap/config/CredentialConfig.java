package com.github.moruke.wall.bootstrap.config;

import com.github.moruke.wall.bootstrap.config.properties.PasswordProp;
import com.github.moruke.wall.identity.authentication.dtos.CredentialMetaDto;
import com.github.moruke.wall.identity.authentication.enums.CredentialTypeEnum;
import com.github.moruke.wall.identity.authentication.service.IAutProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
@Order(1)
public class CredentialConfig {

    @Resource
    private PasswordProp passwordProp;

    @Resource
    private List<IAutProcessor> processors;

    @Bean
    @ConditionalOnProperty(name = "wall.credential.type", havingValue = "password")
    public CredentialMetaDto credentialMetaDto() {
        final CredentialMetaDto credentialMetaDto = new CredentialMetaDto();
        credentialMetaDto.setAlgorithm(passwordProp.getAlgorithm());
        credentialMetaDto.setSaltLength(passwordProp.getSaltLength());
        credentialMetaDto.setIterations(passwordProp.getIterations());
        credentialMetaDto.setKeyLength(passwordProp.getKeyLength());

        return credentialMetaDto;
    }

    @Bean
    public Map<CredentialTypeEnum, IAutProcessor> processor() {
        return processors.stream().collect(Collectors.toMap(IAutProcessor::type, Function.identity()));
    }
}
