package com.github.moruke.wall.account.dao.mapper;

import com.github.moruke.wall.account.dao.entity.User;

import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKey(User record);

    User selectByNameOrCodeAndOrgRootId(@Param("name") String name, @Param("code") String code, @Param("orgRootId") Long orgRootId);

    User selectByNameAndOrgRootId(@Param("name") String name, @Param("orgRootId") Long orgRootId);
}