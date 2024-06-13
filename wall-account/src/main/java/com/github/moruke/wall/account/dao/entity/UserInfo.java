package com.github.moruke.wall.account.dao.entity;

import java.util.Date;
import lombok.Data;

@Data
public class UserInfo {
    private Long id;

    private Long userId;

    private String nickName;

    private String description;

    private String social;

    private String address;

    private String contact;

    private String extra;

    private Long creator;

    private Long mender;

    private Date createTime;

    private Date modifyTime;
}