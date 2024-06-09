package com.github.moruke.wall.auth.dao.mapper;

import com.github.moruke.wall.auth.dao.entity.CasbinPolicy;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface CasbinPolicyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CasbinPolicy record);

    int insertSelective(CasbinPolicy record);

    CasbinPolicy selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CasbinPolicy record);

    int updateByPrimaryKey(CasbinPolicy record);

    int batchInsert(@Param("list") List<CasbinPolicy> list);

    void batchDelete(@Param("ids") List<Long> ids);

    List<CasbinPolicy> selectAllPolicy();

    void deleteAll();
}