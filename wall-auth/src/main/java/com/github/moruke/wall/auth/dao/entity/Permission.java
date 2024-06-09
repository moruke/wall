package com.github.moruke.wall.auth.dao.entity;

import java.util.Date;
import lombok.Data;

@Data
public class Permission {
    private Long id;

    private String description;

    private Byte type;

    private Byte status;

    private String policy;

    private Long externalId;

    private Long domainId;

    private Date expireTime;

    private Long creator;

    private Long mender;

    private Date createTime;

    private Date modifyTime;
}