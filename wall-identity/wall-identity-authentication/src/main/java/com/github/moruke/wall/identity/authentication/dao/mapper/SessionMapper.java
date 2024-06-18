package com.github.moruke.wall.identity.authentication.dao.mapper;

import com.github.moruke.wall.identity.authentication.dao.entity.Session;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SessionMapper {
    int deleteByPrimaryKey(String id);

    int insert(Session record);

    Session selectByPrimaryKey(String id);

    int updateByPrimaryKey(Session record);

    void deleteBySubjectId(@Param("subjectId") String subjectId);

    List<Session> selectBySubjectId(@Param("subjectId") String subjectId);
}