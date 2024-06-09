package com.github.moruke.wall.auth.dao.entity;

import java.util.Date;
import lombok.Data;

@Data
public class Role {
    private Long id;

    private String name;

    private String description;

    private Byte type;

    private Byte status;

    private Long parentId;

    private Long domainId;

    private Long creator;

    private Long mender;

    private Date createTime;

    private Date modifyTime;
}