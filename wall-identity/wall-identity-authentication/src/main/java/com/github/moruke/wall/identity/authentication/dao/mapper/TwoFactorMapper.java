package com.github.moruke.wall.identity.authentication.dao.mapper;

import com.github.moruke.wall.identity.authentication.dao.entity.TwoFactor;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TwoFactorMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TwoFactor record);

    TwoFactor selectByPrimaryKey(Long id);

    int updateByPrimaryKey(TwoFactor record);

    int batchInsert(@Param("list") List<TwoFactor> list);

    List<TwoFactor> selectByUserId(@Param("userId") Long userId);
}