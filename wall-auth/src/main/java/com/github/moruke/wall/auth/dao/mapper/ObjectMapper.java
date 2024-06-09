package com.github.moruke.wall.auth.dao.mapper;

import com.github.moruke.wall.auth.dao.entity.Object;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ObjectMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Object record);

    Object selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Object record);

    int batchInsert(@Param("list") List<Object> list);

    Object selectByNameAndDomain(@Param("name") String name, @Param("domainId") Long domainId);

    int updateParent(@Param("id") Long id, @Param("parentId") Long parentId);

    List<Object> selectByParentId(@Param("parentId") Long parentId);
}