package com.github.moruke.wall.account.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDto {
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

    private String nickName;

    private String description;

    private String social;

    private String address;

    private String contact;

    private String extra;

    private Long infoCreator;

    private Long infoMender;

    private Date infoCreateTime;

    private Date infoModifyTime;
}