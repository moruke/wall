package com.github.moruke.wall.auth.core.impl.casbin;

import com.github.moruke.wall.auth.core.IRbacIJudge;
import com.github.moruke.wall.auth.dtos.permission.Policy;
import com.github.moruke.wall.auth.utils.ConvertUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.casbin.jcasbin.main.Enforcer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class IRbacIJudgeCasbin implements IRbacIJudge {

    @Resource
    private Enforcer rbacEnforcer;

    @Override
    public boolean addRoleForSubject(String subjectId, String role, String domainId) {
        return rbacEnforcer.addRoleForUserInDomain(subjectId, role, domainId);
    }

    @Override
    public boolean removeRoleForSubject(String subjectId, String role, String domainId) {
        return rbacEnforcer.deleteRoleForUserInDomain(subjectId, role, domainId);
    }

    @Override
    public boolean hasRole(String subjectId, String role) {
        return rbacEnforcer.hasRoleForUser(subjectId, role);
    }

    @Override
    public List<String> getRoles(String subjectId, String domainId) {
        return rbacEnforcer.getRolesForUserInDomain(subjectId, domainId);
    }

    @Override
    public List<Pair<String, String>> getRoles() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> getParentRole(String role, String domainId) {
        return rbacEnforcer.getRolesForUserInDomain(role, domainId);
    }

    @Override
    public List<String> getChildrenRoles(String role) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeRoles(String subjectId, List<String> roles, String domainId) {
        roles.forEach(role -> rbacEnforcer.deleteRoleForUserInDomain(subjectId, role, domainId));
    }

    @Override
    public boolean judge(String subjectId, String ObjectId, String action, String domainId) {
        return rbacEnforcer.enforce(subjectId, domainId, ObjectId, action);
    }

    @Override
    public boolean addPermission(String subjectId, String ObjectId, String action, String domainId, String priority, String effect) {
        return rbacEnforcer.addPolicy(subjectId, domainId, ObjectId, action, effect, priority);
    }

    @Override
    public boolean removePermission(String subjectId, String ObjectId, String action, String domainId, String priority, String effect) {
        return rbacEnforcer.removePolicy(subjectId, domainId, ObjectId, action, effect, priority);
    }

    @Override
    public List<Policy> getPermissions(String subjectId, String ObjectId, String domainId) {
        final List<List<String>> permissions = rbacEnforcer.getPermissionsForUserInDomain(subjectId, domainId);
        if (CollectionUtils.isEmpty(permissions)) {
            return Collections.emptyList();
        }

        return permissions.stream().filter(p -> p.get(2).equals(ObjectId)).map(ConvertUtil::convert).collect(Collectors.toList());
    }

    @Override
    public List<Policy> getPermissions(String subjectId, String domainId) {
        final List<List<String>> permissions = rbacEnforcer.getPermissionsForUserInDomain(subjectId, domainId);
        if (CollectionUtils.isEmpty(permissions)) {
            return Collections.emptyList();
        }

        return permissions.stream().map(ConvertUtil::convert).collect(Collectors.toList());
    }

    @Override
    public boolean removeRole(String role) {
        rbacEnforcer.deleteRole(role);
        return true;
    }

    @Override
    public boolean addObjectParent(String id, String parentId, String domainId) {
        return rbacEnforcer.addNamedGroupingPolicy("g2", id, parentId, domainId);
    }

    @Override
    public boolean removeObjectParent(String id, String parentId, String domainId) {
        if ("null".equalsIgnoreCase(parentId) || StringUtils.isBlank(parentId)) {
            // not need to remove
           return true;
        } else {
            return rbacEnforcer.removeNamedGroupingPolicy("g2", id, parentId, domainId);
        }
    }

}
