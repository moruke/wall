package com.github.moruke.wall.identity.authentication.dao.entity;

import java.util.Date;
import lombok.Data;

@Data
public class Session {
    private String id;

    private Byte type;

    private Byte status;

    private String subjectId;

    private Long autHisId;

    private Long rootOrgId;

    private String request;

    private String response;

    private Date createTime;

    private Date modifyTime;

    private Date lastRefresh;
}