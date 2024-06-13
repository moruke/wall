package com.github.moruke.wall.account.dao.mapper;

import com.github.moruke.wall.account.dao.entity.UserGroup;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface UserGroupMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserGroup record);

    UserGroup selectByPrimaryKey(Long id);

    int updateByPrimaryKey(UserGroup record);

    UserGroup selectByNameAndOrgRootId(@Param("name") String name, @Param("orgRootId") Long orgRootId);

    UserGroup selectByNameOrCodeAndOrgRootId(@Param("name") String name, @Param("code") String code, @Param("orgRootId") Long orgRootId);
}