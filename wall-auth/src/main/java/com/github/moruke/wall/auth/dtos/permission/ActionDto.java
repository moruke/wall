package com.github.moruke.wall.auth.dtos.permission;

import com.github.moruke.wall.auth.enums.permission.ActionStatusEnum;
import com.github.moruke.wall.auth.enums.permission.ActionTypeEnum;
import com.github.moruke.wall.common.dto.DomainDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "ActionDto", description = "action dto")
public class ActionDto {
    @ApiModelProperty(value = "action id", dataType = "long")
    private Long id;
    @ApiModelProperty(value = "action name", dataType = "String")
    private String name;
    @ApiModelProperty(value = "action description", dataType = "String")
    private String description;
    @ApiModelProperty(value = "action type", dataType = "String")
    private ActionTypeEnum type;
    @ApiModelProperty(value = "action status", dataType = "String")
    private ActionStatusEnum status;

    @ApiModelProperty(value = "domainDto", dataType = "Object")
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
