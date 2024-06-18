package com.github.moruke.wall.identity.authentication.dao.mapper;

import com.github.moruke.wall.identity.authentication.dao.entity.Credential;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CredentialMapper {
    int insert(Credential record);

    int updateByPrimaryKey(Credential record);

    Credential selectByTypeUIdAndData(@Param("type") Byte type, @Param("subjectId") String subjectId, @Param("data") String data);

    Credential selectBySubjectId(@Param("id") String id);
}