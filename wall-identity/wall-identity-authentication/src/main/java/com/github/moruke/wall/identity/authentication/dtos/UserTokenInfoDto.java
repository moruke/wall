package com.github.moruke.wall.identity.authentication.dtos;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class UserTokenInfoDto extends TokenInfoDto {
    protected Long userId;
    protected String username;
    protected String nickname;
    protected List<Long> orgIds;
    protected List<Long> userGroupIds;

    @Override
    public Map<String, Object> toMap() {
        final Map<String, Object> map = super.toMap();
        map.put("userId", userId);
        map.put("username", username);
        map.put("nickname", nickname);
        map.put("orgIds", orgIds);
        map.put("userGroupIds", userGroupIds);
        return map;
    }
}
