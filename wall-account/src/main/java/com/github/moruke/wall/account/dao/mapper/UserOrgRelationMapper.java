package com.github.moruke.wall.account.dao.mapper;

import com.github.moruke.wall.account.dao.entity.UserOrgRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserOrgRelationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserOrgRelation record);

    UserOrgRelation selectByPrimaryKey(Long id);

    List<UserOrgRelation> selectByOrgId(@Param("id") Long id);

    UserOrgRelation selectByOrgIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);

    List<UserOrgRelation> selectByUserId(@Param("id") Long id);
}