package com.github.moruke.wall.auth.service.impl;

import com.github.moruke.wall.auth.core.IRbacIJudge;
import com.github.moruke.wall.auth.dao.entity.Permission;
import com.github.moruke.wall.auth.dao.entity.Role;
import com.github.moruke.wall.auth.dao.entity.RoleRelation;
import com.github.moruke.wall.auth.dao.mapper.PermissionMapper;
import com.github.moruke.wall.auth.dao.mapper.RoleMapper;
import com.github.moruke.wall.auth.dao.mapper.RoleRelationMapper;
import com.github.moruke.wall.auth.dtos.permission.PermissionDto;
import com.github.moruke.wall.auth.dtos.permission.Policy;
import com.github.moruke.wall.auth.dtos.permission.RoleDto;
import com.github.moruke.wall.auth.service.IRbac;
import com.github.moruke.wall.auth.utils.ConvertUtil;
import com.github.moruke.wall.common.enums.SubjectTypeEnum;
import com.github.moruke.wall.common.utils.Context;
import com.github.moruke.wall.common.utils.Precondition;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.moruke.wall.auth.utils.ConvertUtil.convert;
import static com.github.moruke.wall.common.constant.OperationConstant.ADD_ROLE_FOR_SUBJECT;

@Component
public class RbacImpl implements IRbac {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RoleRelationMapper roleRelationMapper;

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private IRbacIJudge rbacJudge;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addRole(RoleDto dto) {
        Precondition.checkNotNull(dto, "dto is null");

        // check if role already exists
        final Role role = roleMapper.selectByNameAndDomain(dto.getName(), dto.getDomainDto().getId());
        Precondition.checkNull(role, "role already exists");

        final Role newRole = convert(dto);
        roleMapper.insert(newRole);

        return newRole.getId();

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeRole(Long roleId) {
        Precondition.checkArgument(roleId != null && roleId > 0, "roleId must be greater than 0");

        // delete role relations
        roleRelationMapper.deleteByRoleId(roleId);

        roleMapper.deleteByPrimaryKey(roleId);

        // delete role permissions
        return rbacJudge.removeRole(String.valueOf(roleId));

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    // just update role info, not include role relations
    public boolean updateRole(RoleDto dto) {
        Precondition.checkNotNull(dto, "dto is null");
        Precondition.checkArgument(dto.getId() != null && dto.getId() > 0, "id must be greater than 0");

        final Role r = roleMapper.selectByNameAndDomain(dto.getName(), dto.getDomainDto().getId());
        if (r != null && !r.getId().equals(dto.getId())) {
            Precondition.checkNull(r, "role already exists");
        }

        final Role role = convert(dto);

        return roleMapper.updateByPrimaryKeySelective(role) == 1;
    }

    @Override
    public RoleDto getRole(Long roleId) {
        Precondition.checkArgument(roleId != null && roleId > 0, "roleId must be greater than 0");

        final Role role = roleMapper.selectByPrimaryKey(roleId);
        Precondition.checkNotNull(role, "role not found");

        return convert(role);
    }

    @Override
    public RoleDto getRole(String name, Long domainId) {
        Precondition.checkNotNull(name, "name is null");
        Precondition.checkArgument(domainId != null && domainId > 0, "domainId must be greater than 0");

        final Role role = roleMapper.selectByNameAndDomain(name, domainId);
        Precondition.checkNotNull(role, "role not found");

        return convert(role);
    }

    @Override
    public List<RoleDto> getRoles(Long domainId) {
        Precondition.checkArgument(domainId != null && domainId > 0, "domainId must be greater than 0");

        List<Role> roles = roleMapper.selectByDomain(domainId);
        if (CollectionUtils.isEmpty(roles)) {
            return Collections.emptyList();
        }

        return roles.stream().map(ConvertUtil::convert).collect(Collectors.toList());

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addRoleForSubject(Long roleId, Long userId, Long subjectId, SubjectTypeEnum subjectType, Date expireTime) {

        Precondition.checkArgument(roleId != null && roleId > 0, "roleId must be greater than 0");
        Precondition.checkArgument(subjectId != null && subjectId > 0, "subjectId must be greater than 0");

        Precondition.checkArgument(subjectType != SubjectTypeEnum.ROLE, "subjectType must not be ROLE");

        // check if role exists
        final Role role = roleMapper.selectByPrimaryKey(roleId);
        Precondition.checkNotNull(role, "role not found");

        // check if relation exists
        final RoleRelation rr = roleRelationMapper.selectByRoleIdAndSubjectId(roleId, subjectType.getId(subjectId));
        Precondition.checkNull(rr, "role already been added to subject");

        final RoleRelation roleRelation = ConvertUtil.convert(subjectId, subjectType, roleId, userId, expireTime);

        roleRelationMapper.insert(roleRelation);

        Precondition.checkArgument(rbacJudge.addRoleForSubject(subjectType.getId(subjectId), String.valueOf(roleId), String.valueOf(role.getDomainId())), "add role for subject failed");
        final Long permissionId = Context.getPermissionIds().get(0);

        final Permission permission = convert(ADD_ROLE_FOR_SUBJECT, roleId, userId, subjectId, subjectType, permissionId, role.getDomainId(), expireTime);

        permissionMapper.insert(permission);
        return true;

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeRoleForSubject(Long roleId, Long subjectId, SubjectTypeEnum subjectType) {

        // check if role exists
        final Role role = roleMapper.selectByPrimaryKey(roleId);
        Precondition.checkNotNull(role, "role not found");

        final String finalSubjectId = subjectType.getId(subjectId);
        // check relation exists
        final RoleRelation roleRelation = roleRelationMapper.selectByRoleIdAndSubjectId(roleId, finalSubjectId);
        Precondition.checkNotNull(roleRelation, "role relation not found");

        roleRelationMapper.deleteByPrimaryKey(roleRelation.getId());
        final List<Permission> permissions = permissionMapper.selectByPolicies(Collections.singletonList(ConvertUtil.convertToString(subjectId, subjectType, roleId, role.getDomainId())));
        Context.setPermissionIds(permissions.stream().map(Permission::getExternalId).collect(Collectors.toList()));

        Precondition.checkArgument(rbacJudge.removeRoleForSubject(finalSubjectId, String.valueOf(roleId), String.valueOf(role.getDomainId())), "remove role for subject failed");
        permissionMapper.deleteByPrimaryKey(permissions.get(0).getId());
        return true;

    }

    @Override
    public List<RoleDto> getRolesForSubject(Long subjectId, SubjectTypeEnum subjectType) {

        final String finalSubjectId = subjectType.getId(subjectId);
        List<RoleRelation> roleRelations = roleRelationMapper.selectBySubjectId(finalSubjectId);
        if (CollectionUtils.isEmpty(roleRelations)) {
            return Collections.emptyList();
        }

        List<Long> roleIds = roleRelations.stream().map(RoleRelation::getRoleId).collect(Collectors.toList());
        List<Role> roles = roleMapper.selectByIds(roleIds);
        if (CollectionUtils.isEmpty(roles)) {
            // dirty data, delete relations
            roleRelationMapper.deleteByIds(roleIds);
            rbacJudge.removeRoles(finalSubjectId, roleIds.stream().map(String::valueOf).collect(Collectors.toList()), String.valueOf(roles.get(0).getDomainId()));
            return Collections.emptyList();
        }

        return roles.stream().map(ConvertUtil::convert).collect(Collectors.toList());
    }

    @Override
    public boolean addPermissionForSubject(PermissionDto dto) {
        final Permission permission = convert(dto);
        // check if permission already exists
        final List<Permission> permissions = permissionMapper.selectByPolicies(Collections.singletonList(permission.getPolicy()));
        if (CollectionUtils.isNotEmpty(permissions)) {
            return false;
        }

        Precondition.checkArgument(rbacJudge.addPermission(dto.getSubjectType().getId(dto.getSubjectId()), dto.getObjectId().toString(), dto.getActionId().toString(), String.valueOf(dto.getDomainDto().getId()), String.valueOf(dto.getPriority().getCode()), dto.getEffect().name()), "add permission for subject failed");

        final List<Long> permissionIds = Context.getPermissionIds();
        permission.setExternalId(permissionIds.get(0));

        permissionMapper.insert(permission);

        return true;
    }

    @Override
    public boolean removePermissionForSubject(PermissionDto dto) {
        final Permission permission = convert(dto);
        final List<Permission> permissions = permissionMapper.selectByPolicies(Collections.singletonList(permission.getPolicy()));
        if (CollectionUtils.isEmpty(permissions)) {
            return false;
        }

        permissionMapper.deleteByPrimaryKey(permissions.get(0).getId());

        Context.setPermissionIds(Collections.singletonList(permissions.get(0).getExternalId()));

        Precondition.checkArgument(rbacJudge.removePermission(dto.getSubjectType().getId(dto.getSubjectId()), dto.getObjectId().toString(), dto.getActionId().toString(), String.valueOf(dto.getDomainDto().getId()), String.valueOf(dto.getPriority().getCode()), dto.getEffect().name()), "remove permission for subject failed");

        return true;
    }

    @Override
    public List<PermissionDto> getPermissionsForSubject(Long subjectId, SubjectTypeEnum subjectType, Long domainId) {
        final List<Policy> permissions = rbacJudge.getPermissions(subjectType.getId(subjectId), String.valueOf(domainId));
        return getPermissionDtos(permissions);

    }

    @Override
    public List<PermissionDto> getPermissionsForSubject(Long subjectId, SubjectTypeEnum subjectType, Long objectId, Long domainId) {
        final List<Policy> permissions = rbacJudge.getPermissions(subjectType.getId(subjectId), String.valueOf(objectId), String.valueOf(domainId));
        return getPermissionDtos(permissions);
    }

    @Override
    public List<PermissionDto> getPermissionsForObject(Long objectId, Long domainId) {
        return getPermissionDtos(rbacJudge.getPermissionsForObject(objectId, domainId));
    }

    @Override
    public boolean judge(Long subjectId, SubjectTypeEnum subjectType, Long objectId, Long actionId, String domainId) {
        return rbacJudge.judge(subjectType.getId(subjectId), String.valueOf(objectId), String.valueOf(actionId), String.valueOf(domainId));
    }

    private List<PermissionDto> getPermissionDtos(List<Policy> permissions) {
        if (CollectionUtils.isEmpty(permissions)) {
            return Collections.emptyList();
        }

        final List<String> policies = permissions.stream().map(Policy::toPolicy).collect(Collectors.toList());
        final List<Permission> permissionList = permissionMapper.selectByPolicies(policies);
        if (CollectionUtils.isEmpty(permissionList)) {
            return Collections.emptyList();
        }

        return permissionList.stream().map(ConvertUtil::convert).collect(Collectors.toList());
    }
}
