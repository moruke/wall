package com.github.moruke.wall.auth.core;

import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface IRbacIJudge extends IJudge {

    boolean addRoleForSubject(String subjectId, String role, String domainId);

    boolean removeRoleForSubject(String subjectId, String role, String domainId);

    boolean hasRole(String subjectId, String role);

    List<String> getRoles(String subjectId, String domainId);

    List<Pair<String, String>> getRoles();

    List<String> getParentRole(String role, String domainId);

    List<String> getChildrenRoles(String role);

    void removeRoles(String subjectId, List<String> roles, String domainId);

    boolean removeRole(String role);

    boolean addObjectParent(String id, String parentId, String domainId);

    boolean removeObjectParent(String id, String parentId, String domainId);
}
