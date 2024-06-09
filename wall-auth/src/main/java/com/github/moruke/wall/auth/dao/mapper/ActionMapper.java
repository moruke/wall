package com.github.moruke.wall.auth.dao.mapper;

import com.github.moruke.wall.auth.dao.entity.Action;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ActionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Action record);

    Action selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Action record);

    int updateByPrimaryKey(Action record);

    int batchInsert(@Param("list") List<Action> list);

    Action selectByNameAndDomain(@Param("name") String name, @Param("domainId") Long domainId);
}