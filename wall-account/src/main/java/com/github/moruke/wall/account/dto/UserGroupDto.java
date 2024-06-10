package com.github.moruke.wall.account.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserGroupDto {
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