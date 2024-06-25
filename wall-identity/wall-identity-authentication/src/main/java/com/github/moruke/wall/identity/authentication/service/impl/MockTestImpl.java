package com.github.moruke.wall.identity.authentication.service.impl;

import com.github.moruke.wall.common.enums.SubjectTypeEnum;
import com.github.moruke.wall.common.utils.Precondition;
import com.github.moruke.wall.identity.authentication.dao.entity.Credential;
import com.github.moruke.wall.identity.authentication.dao.mapper.CredentialMapper;
import com.github.moruke.wall.identity.authentication.dtos.CredentialMetaDto;
import com.github.moruke.wall.identity.authentication.dtos.LoginRequestDto;
import com.github.moruke.wall.identity.authentication.enums.CredentialTypeEnum;
import com.github.moruke.wall.identity.authentication.service.IAutProcessor;
import com.github.moruke.wall.identity.authentication.service.ICredential;
import com.github.moruke.wall.identity.authentication.utils.Md5Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Order(2)
@Slf4j
public class MockTestImpl implements IAutProcessor, ICredential {
    private static final String NAME = "mockTest";

    @Resource
    private CredentialMapper credentialMapper;

    @Value("${wall.identity.authentication.mock-test.code-length:6}")
    private int codeLength;

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public String data(String salt, CredentialMetaDto meta, String original) {
        return Md5Utils.md5(salt + original);
    }

    @Override
    public String random() {
        final String code = RandomStringUtils.randomNumeric(codeLength);
        log.info("random code: {}", code);
        return code;
    }

    @Override
    public CredentialTypeEnum type() {
        return CredentialTypeEnum.MOCK_TEST;
    }

    @Override
    public void authenticate(LoginRequestDto req) {
        Precondition.checkState(req.getType() == CredentialTypeEnum.MOCK_TEST, "type is invalid");
        final Credential c = credentialMapper.selectBySubjectIdAndType(SubjectTypeEnum.USER.getId(req.getUserId()), CredentialTypeEnum.MOCK_TEST.getCode());
        Precondition.checkNotNull(c, "credential is not exist");
        final String salt = c.getSalt();

        // todo only user at present , need to support other subject
        final String data = data(salt, null, req.getCredentialData());
        final Credential credential = credentialMapper.selectByTypeUIdAndData(CredentialTypeEnum.MOCK_TEST.getCode(), SubjectTypeEnum.USER.getId(req.getUserId()), data);
        Precondition.checkNotNull(credential, "username or password is invalid");

    }
}
