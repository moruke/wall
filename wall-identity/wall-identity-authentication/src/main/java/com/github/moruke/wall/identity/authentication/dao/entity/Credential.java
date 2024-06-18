package com.github.moruke.wall.identity.authentication.dao.entity;

import java.util.Date;
import lombok.Data;

@Data
public class Credential {
    private Long id;

    private String salt;

    private String meta;

    private String data;

    private String subjectId;

    private Byte type;

    private Byte status;

    private Date createTime;

    private Date modifyTime;

    private Long creator;

    private Long mender;
}