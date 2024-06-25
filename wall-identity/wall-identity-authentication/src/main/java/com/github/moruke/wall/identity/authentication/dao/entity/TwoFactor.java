package com.github.moruke.wall.identity.authentication.dao.entity;

import java.util.Date;
import lombok.Data;

@Data
public class TwoFactor {
    private Long id;

    private Long uesrId;

    private Long credentialId;

    private String code;

    private Byte type;

    private Byte status;

    private Date createTime;

    private Date modifyTime;
}