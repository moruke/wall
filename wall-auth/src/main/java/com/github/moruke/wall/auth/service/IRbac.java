package com.github.moruke.wall.auth.service;

import com.github.moruke.wall.auth.dtos.permission.PermissionDto;
import com.github.moruke.wall.auth.dtos.permission.RoleDto;
import com.github.moruke.wall.common.enums.SubjectTypeEnum;

import java.util.Date;
import java.util.List;

public interface IRbac extends Auth{

    Long addRole(RoleDto dto);

    boolean removeRole(Long roleId);

    boolean updateRole(RoleDto dto);

    RoleDto getRole(Long roleId);

    RoleDto getRole(String name, Long domainId);

    List<RoleDto> getRoles(Long domainId);

    boolean addRoleForSubject(Long roleId, Long userId, Long subjectId, SubjectTypeEnum subjectType, Date expireTime);

    boolean removeRoleForSubject(Long roleId, Long subjectId, SubjectTypeEnum subjectType);

    List<RoleDto> getRolesForSubject(Long subjectId, SubjectTypeEnum subjectType);

    boolean addPermissionForSubject (PermissionDto dto);

    boolean removePermissionForSubject (PermissionDto dto);

    List<PermissionDto> getPermissionsForSubject(Long subjectId, SubjectTypeEnum subjectType, Long domainId);

    List<PermissionDto> getPermissionsForSubject(Long subjectId, SubjectTypeEnum subjectType, Long objectId, Long domainId);

}
