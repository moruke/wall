package com.github.moruke.wall.identity.authentication.dao.mapper;

import com.github.moruke.wall.identity.authentication.dao.entity.Credential;
import org.apache.ibatis.annotations.Param;

import java.sql.ResultSet;

public interface CredentialMapper {
    int insert(Credential record);

    int updateByPrimaryKey(Credential record);

    Credential selectByTypeUIdAndData(@Param("type") Byte type, @Param("subjectId") String subjectId, @Param("data") String data);

    Credential selectBySubjectIdAndType(@Param("id") String id, @Param("type") Byte type);

    Credential selectByPrimaryKey(@Param("id") Long credentialId);
}