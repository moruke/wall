package com.github.moruke.wall.identity.authentication.utils;

import com.github.moruke.wall.common.enums.SubjectTypeEnum;
import com.github.moruke.wall.common.utils.JsonUtil;
import com.github.moruke.wall.identity.authentication.dao.entity.Session;
import com.github.moruke.wall.identity.authentication.dtos.LoginRequestDto;
import com.github.moruke.wall.identity.authentication.dtos.LoginResponseDto;
import com.github.moruke.wall.identity.authentication.dtos.SessionDto;
import com.github.moruke.wall.identity.authentication.dtos.UserTokenInfoDto;

import java.util.List;

public class ConvertUtil {
    public static UserTokenInfoDto convert(Long rootOrgId, Long userId, String username, String nickname, List<Long> orgIds, List<Long> ugIds) {
        final UserTokenInfoDto tokenInfoDto = new UserTokenInfoDto();
        tokenInfoDto.setRootOrgId(rootOrgId);
        tokenInfoDto.setType(SubjectTypeEnum.USER);
        tokenInfoDto.setUserId(userId);
        tokenInfoDto.setUsername(username);
        tokenInfoDto.setNickname(nickname);
        tokenInfoDto.setOrgIds(orgIds);
        tokenInfoDto.setUserGroupIds(ugIds);

        return tokenInfoDto;
    }

    public static Session convert(SessionDto dto) {
        final Session session = new Session();
        session.setId(dto.getId());
        session.setType(dto.getType());
        session.setStatus(dto.getStatus());
        session.setSubjectId(dto.getSubjectId());
        session.setAutHisId(dto.getAutHisId());
        session.setRootOrgId(dto.getRootOrgId());
        session.setCreateTime(dto.getCreateTime());
        session.setModifyTime(dto.getModifyTime());
        session.setLastRefresh(dto.getLastRefresh());
        session.setRequest(JsonUtil.writeAsString(dto.getRequest()));
        session.setResponse(JsonUtil.writeAsString(dto.getResponse()));

        return session;
    }

    public static SessionDto convert(Session session) {
        final SessionDto dto = new SessionDto();
        dto.setId(session.getId());
        dto.setType(session.getType());
        dto.setStatus(session.getStatus());
        dto.setSubjectId(session.getSubjectId());
        dto.setAutHisId(session.getAutHisId());
        dto.setRootOrgId(session.getRootOrgId());
        dto.setCreateTime(session.getCreateTime());
        dto.setModifyTime(session.getModifyTime());
        dto.setLastRefresh(session.getLastRefresh());
        dto.setRequest(JsonUtil.readFromString(session.getRequest(), LoginRequestDto.class));
        dto.setResponse(JsonUtil.readFromString(session.getResponse(), LoginResponseDto.class));

        return dto;
    }
}
