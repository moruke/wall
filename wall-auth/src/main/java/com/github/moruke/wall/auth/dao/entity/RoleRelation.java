package com.github.moruke.wall.auth.dao.entity;

import java.util.Date;
import lombok.Data;

@Data
public class RoleRelation {
    private Long id;

    private Long roleId;

    private String subjectId;

    private Byte type;

    private Byte status;

    private Date expireTime;

    private Long creator;

    private Long mender;

    private Date createTime;

    private Date modifyTime;
}