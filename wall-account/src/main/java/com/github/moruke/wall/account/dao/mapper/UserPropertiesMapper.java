package com.github.moruke.wall.account.dao.mapper;

import com.github.moruke.wall.account.dao.entity.UserProperties;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface UserPropertiesMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserProperties record);

    int updateByPrimaryKey(UserProperties record);

    List<UserProperties> selectByUserId(@Param("id") Long id);

    UserProperties selectByUserIdAndProperty(@Param("id") Long id, @Param("property") String property);
}