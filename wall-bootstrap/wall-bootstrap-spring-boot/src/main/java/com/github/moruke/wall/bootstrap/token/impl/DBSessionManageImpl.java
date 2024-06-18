package com.github.moruke.wall.bootstrap.token.impl;

import com.github.moruke.wall.bootstrap.enums.SessionStatusEnum;
import com.github.moruke.wall.bootstrap.enums.SessionTypeEnum;
import com.github.moruke.wall.bootstrap.token.ISessionManager;
import com.github.moruke.wall.common.utils.Precondition;
import com.github.moruke.wall.identity.authentication.dao.entity.Session;
import com.github.moruke.wall.identity.authentication.dao.mapper.SessionMapper;
import com.github.moruke.wall.identity.authentication.dtos.LoginRequestDto;
import com.github.moruke.wall.identity.authentication.dtos.LoginResponseDto;
import com.github.moruke.wall.identity.authentication.dtos.SessionDto;
import com.github.moruke.wall.identity.authentication.utils.ConvertUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class DBSessionManageImpl implements ISessionManager {

    @Resource
    private SessionMapper sessionMapper;

    @Override
    public String add(LoginRequestDto request, LoginResponseDto response, String sessionId) {

        final SessionDto dto = new SessionDto();
        dto.setId(sessionId);
        dto.setRequest(request);
        dto.setResponse(response);
        dto.setType(SessionTypeEnum.NORMAL.getCode());
        dto.setStatus(SessionStatusEnum.NORMAL.getCode());
        dto.setLastRefresh(response.getCreateTime());
        dto.setRootOrgId(response.getRootOrgId());
        dto.setSubjectId(response.getSubjectId());
        dto.setAutHisId(response.getAutHisId());

        final Session session = ConvertUtil.convert(dto);
        sessionMapper.insert(session);
        return String.valueOf(session.getId());
    }

    @Override
    public Boolean remove(String id) {
        return sessionMapper.deleteByPrimaryKey(id) == 1L;
    }

    @Override
    public SessionDto get(String id) {
        final Session session = sessionMapper.selectByPrimaryKey(id);
        Precondition.checkNotNull(session, "session not found");

        if (!SessionStatusEnum.find(session.getStatus()).valid()) {
            throw new IllegalStateException("session status is invalid");
        }

        return ConvertUtil.convert(session);
    }


    @Override
    public boolean validate(String id) {
        final Session session = sessionMapper.selectByPrimaryKey(id);
        if (Objects.isNull(session)) return false;

        return SessionStatusEnum.find(session.getStatus()).valid();

    }

    @Override
    public boolean validate(SessionDto dto) {
        if (Objects.isNull(dto)) return false;

        final Session session = sessionMapper.selectByPrimaryKey(dto.getId());
        if (Objects.isNull(session)) return false;

        return SessionStatusEnum.find(session.getStatus()).valid();

    }

    @Override
    public SessionDto refresh(String id) {
        final Session session = sessionMapper.selectByPrimaryKey(id);
        Precondition.checkNotNull(session, "session not found");

        if (!SessionStatusEnum.find(session.getStatus()).valid()) {
            throw new IllegalStateException("session status is invalid");
        }
        session.setLastRefresh(session.getModifyTime());
        sessionMapper.updateByPrimaryKey(session);
        return ConvertUtil.convert(session);
    }

    @Override
    public void removeBySubjectId(String subjectId) {
        sessionMapper.deleteBySubjectId(subjectId);
    }

    @Override
    public List<SessionDto> getBySubjectId(String subjectId) {
        final List<Session> sessions = sessionMapper.selectBySubjectId(subjectId);
        if (CollectionUtils.isEmpty(sessions)) return Collections.emptyList();

        return sessions.stream().map(ConvertUtil::convert).collect(Collectors.toList());
    }
}
