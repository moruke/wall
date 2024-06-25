package com.github.moruke.wall.identity.authentication.service.impl;

import com.github.moruke.wall.account.service.IUser;
import com.github.moruke.wall.common.enums.SubjectTypeEnum;
import com.github.moruke.wall.common.utils.Precondition;
import com.github.moruke.wall.identity.authentication.dao.entity.Credential;
import com.github.moruke.wall.identity.authentication.dao.mapper.CredentialMapper;
import com.github.moruke.wall.identity.authentication.dtos.CredentialMetaDto;
import com.github.moruke.wall.identity.authentication.dtos.LoginRequestDto;
import com.github.moruke.wall.identity.authentication.enums.CredentialTypeEnum;
import com.github.moruke.wall.identity.authentication.service.IAutProcessor;
import com.github.moruke.wall.identity.authentication.service.ICredential;
import com.github.moruke.wall.identity.authentication.utils.PasswordUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Objects;

@Component
@Order(2)
public class PasswordImpl implements IAutProcessor, ICredential {
    @Resource
    private CredentialMapper credentialMapper;

    @Resource
    @Lazy
    private CredentialMetaDto credentialMetaDto;

    @Resource
    private PasswordUtils passwordUtils;

    @Resource
    private IUser userImpl;

    private static final String NAME = "password";

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public String data(String salt, CredentialMetaDto meta, String original) {

        final String algorithm = meta.getAlgorithm();
        final int saltLength = meta.getSaltLength();
        final int iterations = meta.getIterations();


        final PBEKeySpec spec = new PBEKeySpec(original.toCharArray(), salt.getBytes(), iterations, saltLength);
        ;
        try {
            final SecretKeyFactory factory = SecretKeyFactory.getInstance(algorithm);
            final byte[] hash = factory.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Error while encrypting password", e);
        }
    }

    @Override
    public String random() {
        return Objects.requireNonNull(passwordUtils.random());
    }

    @Override
    public CredentialTypeEnum type() {
        return CredentialTypeEnum.PASSWORD;
    }

    @Override
    public void authenticate(LoginRequestDto req) {
        Precondition.checkState(req.getType() == CredentialTypeEnum.PASSWORD, "type is invalid");
        final Credential c = credentialMapper.selectBySubjectIdAndType(SubjectTypeEnum.USER.getId(req.getUserId()), CredentialTypeEnum.PASSWORD.getCode());
        Precondition.checkNotNull(c, "username or password is invalid");
        final String salt = c.getSalt();

        // todo only user at present , need to support other subject
        final String data = data(salt, credentialMetaDto, req.getCredentialData());
        final Credential credential = credentialMapper.selectByTypeUIdAndData(CredentialTypeEnum.PASSWORD.getCode(), SubjectTypeEnum.USER.getId(req.getUserId()), data);
        Precondition.checkNotNull(credential, "username or password is invalid");
    }

}
