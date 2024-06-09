package com.github.moruke.wall.auth.core;

import com.github.moruke.wall.auth.dtos.permission.Policy;

import java.util.List;

public interface IJudge {

    boolean judge(String subjectId, String ObjectId, String action, String domainId);

    boolean addPermission(String subjectId, String ObjectId, String action, String domainId, String priority, String effect);

    boolean removePermission(String subjectId, String ObjectId, String action, String domainId, String priority, String effect);

    List<Policy> getPermissions(String subjectId, String ObjectId, String domainId);

    List<Policy> getPermissions(String subjectId, String domainId);
}
