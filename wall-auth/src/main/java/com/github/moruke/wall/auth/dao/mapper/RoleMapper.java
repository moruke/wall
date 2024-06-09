package com.github.moruke.wall.auth.dao.mapper;

import com.github.moruke.wall.auth.dao.entity.Role;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface RoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    Role selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Role record);

    int batchInsert(@Param("list") List<Role> list);

    Role selectByNameAndDomain(@Param("name") String name, @Param("domainId") Long domainId);

    List<Role> selectByDomain(@Param("domainId") Long domainId);

    List<Role> selectByIds(@Param("roleIds") List<Long> roleIds);
}