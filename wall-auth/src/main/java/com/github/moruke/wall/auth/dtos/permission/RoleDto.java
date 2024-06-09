package com.github.moruke.wall.auth.dtos.permission;

import com.github.moruke.wall.auth.enums.permission.RoleStatusEnum;
import com.github.moruke.wall.auth.enums.permission.RoleTypeEnum;
import com.github.moruke.wall.common.dto.DomainDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "RoleDto", description = "role dto")
public class RoleDto {
    @ApiModelProperty(value = "role id", dataType = "long")
    private Long id;
    @ApiModelProperty(value = "role name", dataType = "String")
    private String name;
    @ApiModelProperty(value = "role description", dataType = "String")
    private String description;
    @ApiModelProperty(value = "role type", dataType = "RoleTypeEnum")
    private RoleTypeEnum type;
    @ApiModelProperty(value = "role status", dataType = "RoleStatusEnum")
    private RoleStatusEnum status;

    @ApiModelProperty(value = "domainDto", dataType = "DomainDto")
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
