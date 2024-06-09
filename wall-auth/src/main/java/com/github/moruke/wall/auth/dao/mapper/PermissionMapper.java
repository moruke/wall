package com.github.moruke.wall.auth.dao.mapper;

import com.github.moruke.wall.auth.dao.entity.Permission;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface PermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Permission record);

    List<Permission> selectByPolicies(@Param("policies") List<String> policies);
}