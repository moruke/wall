package com.github.moruke.wall.account.dao.entity;

import java.util.Date;
import lombok.Data;

@Data
public class UserUgRelation {
    private Long id;

    private Long userId;

    private Long userGroupId;

    private Byte type;

    private Byte status;

    private Date expireTime;

    private Long creator;

    private Long mender;

    private Date createTime;

    private Date modifyTime;
}