package com.github.moruke.wall.account.dao.entity;

import java.util.Date;
import lombok.Data;

@Data
public class OrganizationProperties {
    private Long id;

    private Long orgId;

    private String property;

    private String value;

    private Byte status;

    private Byte type;

    private Long creator;

    private Long mender;

    private Date createTime;

    private Date modifyTime;
}