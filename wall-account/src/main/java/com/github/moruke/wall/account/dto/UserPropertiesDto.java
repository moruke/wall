package com.github.moruke.wall.account.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserPropertiesDto {
    private Long id;

    private Long userId;

    private String property;

    private String value;

    private Byte status;

    private Byte type;

    private Long creator;

    private Long mender;

    private Date createTime;

    private Date modifyTime;
}
