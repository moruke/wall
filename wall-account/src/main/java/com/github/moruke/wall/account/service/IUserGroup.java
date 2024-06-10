package com.github.moruke.wall.account.service;

import com.github.moruke.wall.account.dto.UserGroupDto;
import com.github.moruke.wall.account.dto.UserGroupPropertiesDto;

import java.util.List;

public interface IUserGroup {
    Long add(UserGroupDto dto);

    boolean remove(Long id, Long mender);

    boolean updateType(Long id, Byte type, Long mender);

    boolean updateStatus(Long id, Byte status, Long mender);

    UserGroupDto get(Long id);

    UserGroupDto get(String name, String code, Long orgRootId);

    List<UserGroupPropertiesDto> getProperties(Long id);

    boolean setProperties(Long id, String property, String value, Long mender);

    List<Long> getUsers(Long id);

    boolean addUser(Long id, Long userId, Long mender);

    boolean removeUser(Long id, Long userId, Long mender);
}
