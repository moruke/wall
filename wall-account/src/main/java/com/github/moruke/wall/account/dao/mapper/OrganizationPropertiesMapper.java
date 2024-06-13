package com.github.moruke.wall.account.dao.mapper;

import com.github.moruke.wall.account.dao.entity.OrganizationProperties;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OrganizationPropertiesMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrganizationProperties record);

    int updateByPrimaryKey(OrganizationProperties record);

    int batchInsert(@Param("list") List<OrganizationProperties> list);

    List<OrganizationProperties> selectByOrgId(@Param("id") Long id);

    OrganizationProperties selectByOrgIdAndProperty(@Param("id") Long id, @Param("property") String property);

    List<OrganizationProperties> selectByOrgIdsAndProperty(@Param("childIds") List<Long> childIds, @Param("property") String property);

    int batchDelete(@Param("list") List<Long> list);
}