package com.github.moruke.wall.identity.authentication.dao.entity;

import java.util.Date;
import lombok.Data;

@Data
public class AuthenticationHistory {
    private Long id;

    private Byte type;

    private Byte status;

    private String subjectId;

    private Date createTime;

    private Date modifyTime;
}