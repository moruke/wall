package com.github.moruke.wall.identity.authentication.dao.mapper;

import com.github.moruke.wall.identity.authentication.dao.entity.AuthenticationHistory;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AuthenticationHistoryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AuthenticationHistory record);

    AuthenticationHistory selectByPrimaryKey(Long id);

    int updateByPrimaryKey(AuthenticationHistory record);

    int batchInsert(@Param("list") List<AuthenticationHistory> list);
}