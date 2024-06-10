package com.github.moruke.wall.account.dao.mapper;

import com.github.moruke.wall.account.dao.entity.UserGroupProperties;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface UserGroupPropertiesMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserGroupProperties record);

    UserGroupProperties selectByPrimaryKey(Long id);

    int updateByPrimaryKey(UserGroupProperties record);

    List<UserGroupProperties> selectByUgId(@Param("id") Long id);

    UserGroupProperties selectByUgIdAndProperty(@Param("id") Long id, @Param("property") String property);
}