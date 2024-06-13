package com.github.moruke.wall.account.dao.mapper;

import com.github.moruke.wall.account.dao.entity.OrganizationInfo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrganizationInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrganizationInfo record);

    OrganizationInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKey(OrganizationInfo record);

    OrganizationInfo selectByOrgId(@Param("id") Long id);

    int deleteByOrgId(@Param("id") Long id);
}