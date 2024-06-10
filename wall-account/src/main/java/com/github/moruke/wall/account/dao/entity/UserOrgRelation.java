package com.github.moruke.wall.account.dao.entity;

import java.util.Date;
import lombok.Data;

@Data
public class UserOrgRelation {
    private Long id;

    private Long userId;

    private Long orgId;

    private Byte type;

    private Byte status;

    private Long creator;

    private Long mender;

    private Date createTime;

    private Date modifyTime;
}