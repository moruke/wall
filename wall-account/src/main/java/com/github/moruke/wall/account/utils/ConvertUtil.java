package com.github.moruke.wall.account.utils;

import com.github.moruke.wall.account.dao.entity.*;
import com.github.moruke.wall.account.dto.*;

public class ConvertUtil {
    public static Organization convert(OrganizationDto dto) {
        Organization entity = new Organization();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setCode(dto.getCode());
        entity.setType(dto.getType());
        entity.setStatus(dto.getStatus());
        entity.setCreateTime(dto.getCreateTime());
        entity.setModifyTime(dto.getModifyTime());
        entity.setParentId(dto.getParentId());
        entity.setRootId(dto.getRootId());
        entity.setCreator(dto.getCreator());
        entity.setMender(dto.getMender());
        return entity;
    }

    public static OrganizationInfo convertToInfo(OrganizationDto dto) {
        OrganizationInfo entity = new OrganizationInfo();
        entity.setOrgId(dto.getId());
        entity.setNickName(dto.getNickName());
        entity.setAddress(dto.getAddress());
        entity.setContact(dto.getContact());
        entity.setDescription(dto.getDescription());
        entity.setExtra(dto.getExtra());
        entity.setCreateTime(dto.getInfoCreateTime());
        entity.setModifyTime(dto.getInfoModifyTime());
        entity.setCreator(dto.getInfoCreator());
        entity.setMender(dto.getInfoMender());

        return entity;
    }

    public static OrganizationDto convertToDto(Organization org, OrganizationInfo orgInfo) {
        final OrganizationDto dto = new OrganizationDto();
        if (org != null) {
            dto.setId(org.getId());
            dto.setName(org.getName());
            dto.setCode(org.getCode());
            dto.setType(org.getType());
            dto.setStatus(org.getStatus());
            dto.setCreateTime(org.getCreateTime());
            dto.setModifyTime(org.getModifyTime());
            dto.setParentId(org.getParentId());
            dto.setRootId(org.getRootId());
            dto.setCreator(org.getCreator());
            dto.setMender(org.getMender());
        }

        if (orgInfo != null) {
            dto.setNickName(orgInfo.getNickName());
            dto.setAddress(orgInfo.getAddress());
            dto.setContact(orgInfo.getContact());
            dto.setDescription(orgInfo.getDescription());
            dto.setExtra(orgInfo.getExtra());
            dto.setInfoCreateTime(orgInfo.getCreateTime());
            dto.setInfoModifyTime(orgInfo.getModifyTime());
            dto.setInfoCreator(orgInfo.getCreator());
            dto.setInfoMender(orgInfo.getMender());
        }
        return dto;
    }

    public static OrgPropertiesDto convert(OrganizationProperties org) {
        OrgPropertiesDto dto = new OrgPropertiesDto();
        dto.setId(org.getId());
        dto.setOrgId(org.getOrgId());
        dto.setProperty(org.getProperty());
        dto.setValue(org.getValue());
        dto.setCreateTime(org.getCreateTime());
        dto.setModifyTime(org.getModifyTime());
        dto.setCreator(org.getCreator());
        dto.setMender(org.getMender());
        return dto;
    }

    public static UserGroup convert(UserGroupDto dto) {
        UserGroup entity = new UserGroup();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setCode(dto.getCode());
        entity.setOrgRootId(dto.getOrgRootId());
        entity.setDescription(dto.getDescription());
        entity.setType(dto.getType());
        entity.setStatus(dto.getStatus());
        entity.setCreateTime(dto.getCreateTime());
        entity.setModifyTime(dto.getModifyTime());
        entity.setCreator(dto.getCreator());
        entity.setMender(dto.getMender());
        return entity;
    }


    public static UserGroupDto convert(UserGroup ug) {
        UserGroupDto dto = new UserGroupDto();
        dto.setId(ug.getId());
        dto.setName(ug.getName());
        dto.setCode(ug.getCode());
        dto.setOrgRootId(ug.getOrgRootId());
        dto.setType(ug.getType());
        dto.setStatus(ug.getStatus());
        dto.setCreateTime(ug.getCreateTime());
        dto.setModifyTime(ug.getModifyTime());
        dto.setCreator(ug.getCreator());
        dto.setMender(ug.getMender());
        return dto;
    }

    public static UserGroupPropertiesDto convert(UserGroupProperties userGroupProperties) {
        UserGroupPropertiesDto dto = new UserGroupPropertiesDto();
        dto.setId(userGroupProperties.getId());
        dto.setUgId(userGroupProperties.getUserGroupId());
        dto.setProperty(userGroupProperties.getProperty());
        dto.setValue(userGroupProperties.getValue());
        dto.setCreateTime(userGroupProperties.getCreateTime());
        dto.setModifyTime(userGroupProperties.getModifyTime());
        dto.setCreator(userGroupProperties.getCreator());
        dto.setMender(userGroupProperties.getMender());
        dto.setType(userGroupProperties.getType());
        dto.setStatus(userGroupProperties.getStatus());
        return dto;

    }

    public static UserInfo convertToInfo(UserDto dto) {
        UserInfo entity = new UserInfo();
        entity.setNickName(dto.getNickName());
        entity.setAddress(dto.getAddress());
        entity.setContact(dto.getContact());
        entity.setDescription(dto.getDescription());
        entity.setExtra(dto.getExtra());
        entity.setCreateTime(dto.getInfoCreateTime());
        entity.setModifyTime(dto.getInfoModifyTime());
        entity.setCreator(dto.getInfoCreator());
        entity.setMender(dto.getInfoMender());
        return entity;
    }

    public static User convert(UserDto dto) {
        User entity = new User();
        entity.setId(dto.getId());
        entity.setOrgRootId(dto.getOrgRootId());
        entity.setCode(dto.getCode());
        entity.setName(dto.getName());
        entity.setType(dto.getType());
        entity.setStatus(dto.getStatus());
        entity.setSourceType(dto.getSourceType());
        entity.setCreator(dto.getCreator());
        entity.setMender(dto.getMender());
        entity.setCreateTime(dto.getCreateTime());
        entity.setModifyTime(dto.getModifyTime());
        return entity;
    }

    public static UserDto convertToDto(User user, UserInfo userInfo) {
        UserDto dto = new UserDto();
        if (user != null) {
            dto.setId(user.getId());
            dto.setOrgRootId(user.getOrgRootId());
            dto.setCode(user.getCode());
            dto.setName(user.getName());
            dto.setType(user.getType());
            dto.setStatus(user.getStatus());
            dto.setSourceType(user.getSourceType());
            dto.setCreator(user.getCreator());
            dto.setMender(user.getMender());
            dto.setCreateTime(user.getCreateTime());
            dto.setModifyTime(user.getModifyTime());
        }

        if (userInfo != null) {
            dto.setNickName(userInfo.getNickName());
            dto.setAddress(userInfo.getAddress());
            dto.setContact(userInfo.getContact());
            dto.setDescription(userInfo.getDescription());
            dto.setExtra(userInfo.getExtra());
            dto.setInfoCreateTime(userInfo.getCreateTime());
            dto.setInfoModifyTime(userInfo.getModifyTime());
            dto.setInfoCreator(userInfo.getCreator());
            dto.setInfoMender(userInfo.getMender());
        }
        return dto;
    }

    public static UserPropertiesDto convert(UserProperties userProperties) {
        UserPropertiesDto dto = new UserPropertiesDto();
        dto.setId(userProperties.getId());
        dto.setUserId(userProperties.getUserId());
        dto.setType(userProperties.getType());
        dto.setStatus(userProperties.getStatus());
        dto.setProperty(userProperties.getProperty());
        dto.setValue(userProperties.getValue());
        dto.setCreateTime(userProperties.getCreateTime());
        dto.setModifyTime(userProperties.getModifyTime());
        dto.setCreator(userProperties.getCreator());
        dto.setMender(userProperties.getMender());
        return dto;
    }
}
