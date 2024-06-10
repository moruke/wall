package com.github.moruke.wall.account.dao.entity;

import java.util.Date;
import lombok.Data;

@Data
public class UserGroup {
    private Long id;

    private Long orgRootId;

    private String code;

    private String name;

    private String description;

    private Byte type;

    private Byte status;

    private Long creator;

    private Long mender;

    private Date createTime;

    private Date modifyTime;
}