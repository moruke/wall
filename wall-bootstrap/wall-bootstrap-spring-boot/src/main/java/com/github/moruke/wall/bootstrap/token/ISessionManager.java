package com.github.moruke.wall.bootstrap.token;

import com.github.moruke.wall.identity.authentication.dtos.LoginRequestDto;
import com.github.moruke.wall.identity.authentication.dtos.LoginResponseDto;
import com.github.moruke.wall.identity.authentication.dtos.SessionDto;

import java.util.List;
import java.util.UUID;

public interface ISessionManager {
    String add(LoginRequestDto request, LoginResponseDto response, String sessionId);

    Boolean remove(String id);

    SessionDto get(String id);

    boolean validate(String id);

    boolean validate(SessionDto dto);

    SessionDto refresh(String id);

    void removeBySubjectId(String subjectId);

    List<SessionDto> getBySubjectId(String subjectId);

}
