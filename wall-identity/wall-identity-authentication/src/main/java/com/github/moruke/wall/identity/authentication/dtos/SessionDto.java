package com.github.moruke.wall.identity.authentication.dtos;

import lombok.Data;

import java.util.Date;
@Data
public class SessionDto {
    private String id;

    private Byte type;

    private Byte status;

    private String subjectId;

    private Long autHisId;

    private Long rootOrgId;

    private LoginRequestDto request;

    private LoginResponseDto response;

    private Date createTime;

    private Date modifyTime;

    private Date lastRefresh;
}
