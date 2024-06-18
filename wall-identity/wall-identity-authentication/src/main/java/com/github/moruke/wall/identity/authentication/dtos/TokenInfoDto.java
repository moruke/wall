package com.github.moruke.wall.identity.authentication.dtos;

import com.github.moruke.wall.common.enums.SubjectTypeEnum;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public abstract class TokenInfoDto {
    protected Long rootOrgId;
    protected SubjectTypeEnum type;

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("rootOrgId", rootOrgId);
        map.put("type", type.getName());
        return map;
    }
}
