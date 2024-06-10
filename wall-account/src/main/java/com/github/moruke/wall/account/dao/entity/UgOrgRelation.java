package com.github.moruke.wall.account.dao.entity;

import java.util.Date;
import lombok.Data;

@Data
public class UgOrgRelation {
    private Long id;

    private Long userGroupId;

    private Long orgId;

    private Byte type;

    private Byte status;

    private Date expireTime;

    private Long creator;

    private Long mender;

    private Date createTime;

    private Date modifyTime;
}