package com.github.moruke.wall.common.utils;

import com.github.moruke.wall.common.dto.DomainDto;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class Context {

    private static ThreadLocal<DomainDto> DOMAIN = new ThreadLocal<>();

    private static ThreadLocal<List<Long>> PERMISSION_IDS = new ThreadLocal<>();

    private static ThreadLocal<Long> SUBJECT_ID = new ThreadLocal<>();

    public static DomainDto getDomain() {
        return requireNonNull(DOMAIN.get(), "domain is null");
    }

    public static void setDomain(DomainDto domainDto) {
        Precondition.checkNotNull(domainDto, "domain is null");
        DOMAIN.set(domainDto);
    }

    public static List<Long> getPermissionIds() {
        return requireNonNull(PERMISSION_IDS.get(), "permissionIds is null");
    }

    public static void setPermissionIds(List<Long> permissionIds) {
        Precondition.checkNotNull(permissionIds, "permissionIds is null");
        PERMISSION_IDS.set(permissionIds);
    }

    public static long getSubjectId() {
        return requireNonNull(SUBJECT_ID.get(), "subjectId is null");
    }
}
