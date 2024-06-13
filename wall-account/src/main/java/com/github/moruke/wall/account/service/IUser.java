package com.github.moruke.wall.account.service;

import com.github.moruke.wall.account.dto.UserDto;
import com.github.moruke.wall.account.dto.UserPropertiesDto;

import java.util.List;

public interface IUser {
    Long add(UserDto dto);

    boolean remove(Long id, Long mender);

    boolean updateType(Long id, Byte type, Long mender);

    boolean updateStatus(Long id, Byte status, Long mender);

    boolean updateInfo(UserDto dto);

    UserDto get(Long id);

    UserDto get(String name, String code, Long orgRootId);

    List<UserPropertiesDto> getProperties(Long id);

    boolean setProperties(Long id, String property, String value, Long mender);


}
