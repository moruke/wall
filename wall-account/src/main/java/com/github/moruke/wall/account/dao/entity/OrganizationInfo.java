package com.github.moruke.wall.account.dao.entity;

import java.util.Date;
import lombok.Data;

@Data
public class OrganizationInfo {
    private Long id;

    private Long orgId;

    private String nickName;

    private String description;

    private String address;

    private String contact;

    private String extra;

    private Long creator;

    private Long mender;

    private Date createTime;

    private Date modifyTime;
}