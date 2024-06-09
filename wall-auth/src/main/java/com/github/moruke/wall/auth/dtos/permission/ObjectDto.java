package com.github.moruke.wall.auth.dtos.permission;

import com.github.moruke.wall.auth.enums.permission.ObjectStatusEnum;
import com.github.moruke.wall.auth.enums.permission.ObjectTypeEnum;
import com.github.moruke.wall.common.dto.DomainDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "ObjectDto", description = "object dto")
public class ObjectDto {
    @ApiModelProperty(value = "object id", dataType = "long")
    private Long id;
    @ApiModelProperty(value = "object name", dataType = "String")
    private String name;
    @ApiModelProperty(value = "object description", dataType = "String")
    private String description;
    @ApiModelProperty(value = "object type", dataType = "String")
    private ObjectTypeEnum type;
    @ApiModelProperty(value = "object status", dataType = "String")
    private ObjectStatusEnum status;

    @ApiModelProperty(value = "domain", dataType = "DomainDto")
    private DomainDto domainDto;

    @ApiModelProperty(value = "creator user id", dataType = "long")
    private Long creator;
    @ApiModelProperty(value = "mender user id", dataType = "long")
    private Long mender;

    @ApiModelProperty(value = "create time", dataType = "Date")
    private Date createTime;
    @ApiModelProperty(value = "modify time", dataType = "Date")
    private Date modifyTime;
}
