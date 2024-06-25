package com.github.moruke.wall.identity.authentication.service.impl;

import com.github.moruke.wall.account.dto.OrganizationDto;
import com.github.moruke.wall.account.dto.UserDto;
import com.github.moruke.wall.account.dto.UserPropertiesDto;
import com.github.moruke.wall.account.service.IOrganization;
import com.github.moruke.wall.account.service.IUser;
import com.github.moruke.wall.common.enums.SubjectTypeEnum;
import com.github.moruke.wall.common.utils.JsonUtil;
import com.github.moruke.wall.common.utils.Precondition;
import com.github.moruke.wall.identity.authentication.aspect.Authentication;
import com.github.moruke.wall.identity.authentication.dao.entity.AuthenticationHistory;
import com.github.moruke.wall.identity.authentication.dao.entity.Credential;
import com.github.moruke.wall.identity.authentication.dao.entity.TwoFactor;
import com.github.moruke.wall.identity.authentication.dao.mapper.AuthenticationHistoryMapper;
import com.github.moruke.wall.identity.authentication.dao.mapper.CredentialMapper;
import com.github.moruke.wall.identity.authentication.dao.mapper.TwoFactorMapper;
import com.github.moruke.wall.identity.authentication.dtos.CredentialMetaDto;
import com.github.moruke.wall.identity.authentication.dtos.LoginRequestDto;
import com.github.moruke.wall.identity.authentication.dtos.LoginResponseDto;
import com.github.moruke.wall.identity.authentication.dtos.UserTokenInfoDto;
import com.github.moruke.wall.identity.authentication.enums.CredentialStatusEnum;
import com.github.moruke.wall.identity.authentication.enums.CredentialTypeEnum;
import com.github.moruke.wall.identity.authentication.enums.LoginStatusEnum;
import com.github.moruke.wall.identity.authentication.service.IAutProcessor;
import com.github.moruke.wall.identity.authentication.service.IAuthentication;
import com.github.moruke.wall.identity.authentication.service.ICredential;
import com.github.moruke.wall.identity.authentication.service.IToken;
import com.github.moruke.wall.identity.authentication.utils.ConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static com.github.moruke.wall.common.constant.AuthenticationConstant.TOW_FACTOR_AUTHENTICATION;

@Component
@Slf4j
public class AuthenticationImpl implements IAuthentication {

    @Resource
    private IOrganization organizationImpl;

    @Resource
    private IUser userImpl;

    @Resource
    private CredentialMetaDto credentialMetaDto;

    @Resource
    private AuthenticationHistoryMapper authenticationHistoryMapper;

    @Resource
    private TwoFactorMapper twoFactorMapper;

    @Resource
    private Map<CredentialTypeEnum, IAutProcessor> processor;

//    @Resource
    private ICredential credentialImpl;

    @Resource
    private CredentialMapper credentialMapper;

    @Resource
    private IToken tokenImpl;

    @Resource
    private PasswordImpl passwordImpl;

    private Map<String, UserDto> sessionCodeUserMap = new ConcurrentHashMap<>();

    @Override
    @Authentication
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        byte status = LoginStatusEnum.FAILURE.getCode();

        LoginResponseDto response = null;

        try {
            check(loginRequestDto);

            final String sessionCode = loginRequestDto.getSessionCode();

            UserDto userDto = null;
            final String rootOrgName = loginRequestDto.getRootOrgName();
            final OrganizationDto org = organizationImpl.getRootOrgByName(rootOrgName);

            if (StringUtils.isBlank(sessionCode)) {
                // need check password first
                Precondition.checkState(organizationImpl.checkValid(org.getId(), org.getRootId()), "rootOrgName is invalid");
                loginRequestDto.setRootOrgId(org.getId());

                userDto  = userImpl.get(loginRequestDto.getUsername(), null, org.getId());
                Precondition.checkNotNull(userDto, "username or password is invalid");
                loginRequestDto.setUserId(userDto.getId());

                final CredentialTypeEnum type = loginRequestDto.getType();
                processor.get(type).authenticate(loginRequestDto);
            } else {
                userDto = sessionCodeUserMap.get(sessionCode);
            }

            final List<TwoFactor> twoFactors = twoFactorMapper.selectByUserId(userDto.getId());

            if (CollectionUtils.isNotEmpty(twoFactors) && twoFactors.stream().noneMatch(twoFactor -> Objects.equals(credentialMapper.selectByPrimaryKey(twoFactor.getCredentialId()).getType(), loginRequestDto.getType().getCode()))) {

                final String _sessionCode = UUID.randomUUID().toString();
                sessionCodeUserMap.put(_sessionCode, userDto);

                final List<String> codes = twoFactors.stream().map(TwoFactor::getCode).collect(Collectors.toList());
                status = LoginStatusEnum.PROCESSING.getCode();
                response = new LoginResponseDto();

                response.setTwoFactors(codes);
                response.setSubjectId(SubjectTypeEnum.USER.getId(userDto.getId()));
                response.setUserId(userDto.getId());
                response.setRootOrgId(org.getId());
                response.setSessionCode(_sessionCode);
                response.setStatus(status);

            } else {
                // todo only user at present
                final List<Long> orgIds = userImpl.getOrgIds(userDto.getId());
                final List<Long> ugIds = userImpl.getUgIds(userDto.getId());
                final UserTokenInfoDto user = ConvertUtil.convert(org.getRootId(), userDto.getId(), userDto.getName(), userDto.getNickName(), orgIds, ugIds);

                final String token = tokenImpl.generateToken(user);
                final String refreshToken = tokenImpl.generateRefreshToken(user);

                status = LoginStatusEnum.SUCCESS.getCode();

                response = new LoginResponseDto();
                response.setToken(token);
                response.setRefreshToken(refreshToken);
                response.setSubjectId(SubjectTypeEnum.USER.getId(userDto.getId()));
                response.setUserId(userDto.getId());
                response.setRootOrgId(org.getId());
                response.setStatus(status);

            }

            return response;
        } catch (Exception e) {
            status = LoginStatusEnum.FAILURE.getCode();
            throw new RuntimeException("Login failed", e);
        } finally {
            final AuthenticationHistory history = new AuthenticationHistory();
            history.setSubjectId(SubjectTypeEnum.USER.getId(loginRequestDto.getUserId()));
            history.setType(loginRequestDto.getType().getCode());
            history.setStatus(status);

            authenticationHistoryMapper.insert(history);

            if (Objects.nonNull(response)) {
                response.setAutHisId(history.getId());
            }
        }

    }

    @Override
    public LoginResponseDto refreshToken(String refreshToken) {
        return null;
    }

    @Override
    public void logoutAll(String username) {

    }

    @Override
    public void logoutOne(String username, String clientId) {

    }

    @Override
    public String random(String subjectId) {
        // only user at present
        final Long userId = SubjectTypeEnum.extra(subjectId).getId(subjectId);
        final String random = credentialImpl.random();
        final String salt = userImpl.salt(userId);
        final String data = passwordImpl.data(salt, credentialMetaDto, random);


        final Credential credential = new Credential();
        credential.setSubjectId(subjectId);
        credential.setSalt(salt);
        credential.setData(data);
        credential.setMeta(JsonUtil.writeAsString(credentialMetaDto));
        credential.setType(CredentialTypeEnum.PASSWORD.getCode());
        credential.setStatus(CredentialStatusEnum.NORMAL.getCode());
        credential.setCreator(userId);
        credential.setMender(userId);

        Precondition.checkArgument(credentialMapper.insert(credential) == 1, "random failed");

        return random;
    }

    @Override
    public LoginResponseDto bind(LoginRequestDto dto) {
        // bind user and third party account
        return null;
    }


    private void check(LoginRequestDto loginRequestDto) {

        log.warn("not implemented yet");

        // todo check code valid

        // todo just support postman now.
        final String clientId = loginRequestDto.getClientId();
        Precondition.checkArgument("postman".equals(clientId), "clientId is invalid");

        if (StringUtils.isNotBlank(loginRequestDto.getSessionCode()) && !sessionCodeUserMap.containsKey(loginRequestDto.getSessionCode())) {
            // sessionCode is invalid or expired, must login again
            throw new RuntimeException("sessionCode is invalid or expired");
        }

        // todo check redirectUri valid

        // todo check scope valid

        // todo check refreshToken valid

        // todo check grantType valid

    }
}
