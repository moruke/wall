package com.github.moruke.wall.account.dao.mapper;

import com.github.moruke.wall.account.dao.entity.Organization;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OrganizationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Organization record);

    Organization selectByPrimaryKey(Long id);

    int updateByPrimaryKey(Organization record);

    Object selectByNameAndRootId(@Param("name") String name, @Param("rootId") Long rootId);

    List<Organization> selectByParentId(@Param("id") Long id);

    Organization selectByNameOrCodeAndRootId(@Param("name") String name, @Param("code") String code, @Param("rootId") Long rootId);
}