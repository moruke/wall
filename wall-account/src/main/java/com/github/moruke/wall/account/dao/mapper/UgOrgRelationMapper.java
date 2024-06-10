package com.github.moruke.wall.account.dao.mapper;

import com.github.moruke.wall.account.dao.entity.UgOrgRelation;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UgOrgRelationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UgOrgRelation record);

    UgOrgRelation selectByPrimaryKey(Long id);

    List<UgOrgRelation> selectByOrgId(@Param("id") Long id);

    UgOrgRelation selectByOrgIdAndUserGroupId(@Param("id") Long id, @Param("userGroupId") Long userGroupId);

    List<UgOrgRelation> selectByUgId(@Param("id") Long id);
}