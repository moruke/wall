package com.github.moruke.wall.account.dao.mapper;

import com.github.moruke.wall.account.dao.entity.UserUgRelation;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserUgRelationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserUgRelation record);

    UserUgRelation selectByPrimaryKey(Long id);

    List<UserUgRelation> selectByUgId(@Param("id") Long id);

    UserUgRelation selectByUgIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);

    List<UserUgRelation> selectByUserId(@Param("id") Long id);
}