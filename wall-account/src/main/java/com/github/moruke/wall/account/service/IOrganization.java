package com.github.moruke.wall.account.service;

import com.github.moruke.wall.account.dto.OrgPropertiesDto;
import com.github.moruke.wall.account.dto.OrganizationDto;
import com.github.moruke.wall.account.dto.UserDto;

import java.util.List;

public interface IOrganization {
    Long add(OrganizationDto dto);

    boolean remove(Long id, Long mender);

    boolean updateType(Long id, Byte type, Long mender);

    boolean updateStatus(Long id, Byte status, Long mender);

    boolean updateParent(Long id, Long parentId, Long mender);

    boolean updateInfo(OrganizationDto dto);

    OrganizationDto get(Long id);

    OrganizationDto get(String name, String code, Long rootId);

    List<OrganizationDto> getChildren(Long parentId);

    OrganizationDto getRoot(Long id);

    OrganizationDto getParent(Long id);

    List<OrgPropertiesDto> getProperties(Long id);

    boolean setProperties(Long id, String property, String value, boolean overrideChildren, Long mender);

    List<Long> getUsers(Long id);

    boolean addUser(Long id, Long userId, Long mender);

    boolean removeUser(Long id, Long userId, Long mender);

    List<Long> getUserGroups(Long id);

    boolean addUserGroup(Long id, Long userGroupId, Long mender);

    boolean removeUserGroup(Long id, Long userGroupId, Long mender);
}
