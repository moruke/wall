package com.github.moruke.wall.account.dao.entity;

import java.util.Date;
import lombok.Data;

@Data
public class User {
    private Long id;

    private Long orgRootId;

    private String code;

    private String name;

    private Byte type;

    private Byte status;

    private Byte sourceType;

    private Long creator;

    private Long mender;

    private Date createTime;

    private Date modifyTime;
}