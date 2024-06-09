package com.github.moruke.wall.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DomainDto {
    @ApiModelProperty(value = "domainId", dataType = "long")
    private Long id;

    public DomainDto() {
    }

    public DomainDto(Long id) {
        this.id = id;
    }
}
