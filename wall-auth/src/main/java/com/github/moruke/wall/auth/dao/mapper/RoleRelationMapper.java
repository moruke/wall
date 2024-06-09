package com.github.moruke.wall.auth.dao.mapper;

import com.github.moruke.wall.auth.dao.entity.RoleRelation;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface RoleRelationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RoleRelation record);

    RoleRelation selectByPrimaryKey(Long id);

    int batchInsert(@Param("list") List<RoleRelation> list);

    RoleRelation selectByRoleIdAndSubjectId(@Param("roleId") Long roleId, @Param("subjectId") String subjectId);

    List<RoleRelation> selectBySubjectId(@Param("subjectId") String subjectId);

    void deleteByIds(@Param("roleIds") List<Long> roleIds);

    void deleteByRoleId(@Param("roleId") Long roleId);
}