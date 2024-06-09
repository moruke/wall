package com.github.moruke.wall.auth.dtos.permission;

import com.github.moruke.wall.auth.enums.permission.*;
import com.github.moruke.wall.common.dto.DomainDto;
import com.github.moruke.wall.common.enums.SubjectTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "PermissionDto", description = "permission dto")
public class PermissionDto {
    @ApiModelProperty(value = "permission id", dataType = "long")
    private Long id;

    @ApiModelProperty(value = "subject id", dataType = "long")
    private Long subjectId;
    @ApiModelProperty(value = "subject type", dataType = "SubjectTypeEnum")
    private SubjectTypeEnum subjectType;

    @ApiModelProperty(value = "object id", dataType = "long")
    private Long objectId;
    @ApiModelProperty(value = "action id", dataType = "long")
    private Long actionId;

    @ApiModelProperty(value = "permission description", dataType = "String")
    private String description;

    @ApiModelProperty(value = "permission type", dataType = "PermissionTypeEnum")
    private PermissionTypeEnum type;
    @ApiModelProperty(value = "permission status", dataType = "PermissionStatusEnum")
    private PermissionStatusEnum status;

    @ApiModelProperty(value = "domainDto", dataType = "DomainDto")
    private DomainDto domainDto;

    @ApiModelProperty(value = "priority", dataType = "PriorityEnum")
    private PriorityEnum priority = PriorityEnum.LOWEST;

    @ApiModelProperty(value = "effect", dataType = "EffectTypeEnum")
    private EffectTypeEnum effect;

    @ApiModelProperty(value = "creator user id", dataType = "long")
    private Long creator;
    @ApiModelProperty(value = "mender user id", dataType = "long")
    private Long mender;

    @ApiModelProperty(value = "permission expire time", dataType = "Date")
    private Date expireTime;
    @ApiModelProperty(value = "create time", dataType = "Date")
    private Date createTime;
    @ApiModelProperty(value = "modify time", dataType = "Date")
    private Date modifyTime;
}
