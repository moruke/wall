package com.github.moruke.wall.account.dao.entity;

import java.util.Date;
import lombok.Data;

@Data
public class Organization {
    private Long id;

    private String code;

    private String name;

    private Byte type;

    private Byte status;

    private Long parentId;

    private Long rootId;

    private Long creator;

    private Long mender;

    private Date createTime;

    private Date modifyTime;
}