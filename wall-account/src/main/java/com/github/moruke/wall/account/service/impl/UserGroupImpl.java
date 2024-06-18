package com.github.moruke.wall.account.service.impl;

import com.github.moruke.wall.account.dao.entity.UgOrgRelation;
import com.github.moruke.wall.account.dao.entity.UserGroup;
import com.github.moruke.wall.account.dao.entity.UserGroupProperties;
import com.github.moruke.wall.account.dao.entity.UserUgRelation;
import com.github.moruke.wall.account.dao.mapper.UgOrgRelationMapper;
import com.github.moruke.wall.account.dao.mapper.UserGroupMapper;
import com.github.moruke.wall.account.dao.mapper.UserGroupPropertiesMapper;
import com.github.moruke.wall.account.dao.mapper.UserUgRelationMapper;
import com.github.moruke.wall.account.dto.UserGroupDto;
import com.github.moruke.wall.account.dto.UserGroupPropertiesDto;
import com.github.moruke.wall.account.enums.*;
import com.github.moruke.wall.account.service.IUserGroup;
import com.github.moruke.wall.account.utils.ConvertUtil;
import com.github.moruke.wall.common.utils.DateUtil;
import com.github.moruke.wall.common.utils.NameUtil;
import com.github.moruke.wall.common.utils.Precondition;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class UserGroupImpl implements IUserGroup {

    @Resource
    private UserGroupMapper userGroupMapper;

    @Resource
    private UserGroupPropertiesMapper userGroupPropertiesMapper;

    @Resource
    private UserUgRelationMapper userUgRelationMapper;

    @Resource
    private UgOrgRelationMapper ugOrgRelationMapper;

    @Value("${wall.rules.ug.name-length}")
    private int nameLength;

    @Value("#{'${wall.rules.ug.name-rules}'.split(',')}")
    private List<String> nameRules;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long add(UserGroupDto dto) {
        Precondition.checkArgument(NameUtil.checkLength(dto.getName(), nameLength), "UserGroup name is too long");
        Precondition.checkArgument(NameUtil.checkRule(dto.getName(), nameRules), "UserGroup name does not meet the rules");

        final UserGroup ug = ConvertUtil.convert(dto);

        ug.setCode(UUID.randomUUID().toString());

        Precondition.checkNull(userGroupMapper.selectByNameAndOrgRootId(dto.getName(), dto.getOrgRootId()), "UserGroup name already exists");
        Precondition.checkArgument(userGroupMapper.insert(ug) == 1, "UserGroup add failed");

        return ug.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean remove(Long id, Long mender) {

        Precondition.checkNotNull(userGroupMapper.selectByPrimaryKey(id), "UserGroup does not exist");

        final List<UserUgRelation> userUgRelations = userUgRelationMapper.selectByUgId(id);
        final List<UgOrgRelation> ugOrgRelations = ugOrgRelationMapper.selectByUgId(id);

        if (CollectionUtils.isEmpty(userUgRelations) && CollectionUtils.isEmpty(ugOrgRelations)) {
            // todo mark delete
            return userGroupMapper.deleteByPrimaryKey(id) == 1;
        } else {
            Precondition.checkArgument(CollectionUtils.isEmpty(userUgRelations), "UserGroup has user");
            Precondition.checkArgument(CollectionUtils.isEmpty(ugOrgRelations), "UserGroup has organization");
        }

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateType(Long id, Byte type, Long mender) {
        Precondition.checkArgument(id != null && id > 0, "UserGroup id is invalid");

        final UserGroup ug = userGroupMapper.selectByPrimaryKey(id);
        Precondition.checkNotNull(ug, "UserGroup does not exist");

        ug.setType(UserGroupTypeEnum.find(type).getCode());
        ug.setMender(mender);

        return userGroupMapper.updateByPrimaryKey(ug) == 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateStatus(Long id, Byte status, Long mender) {
        Precondition.checkArgument(id != null && id > 0, "UserGroup id is invalid");

        final UserGroup ug = userGroupMapper.selectByPrimaryKey(id);
        Precondition.checkNotNull(ug, "UserGroup does not exist");

        ug.setStatus(UserGroupStatusEnum.find(status).getCode());
        ug.setMender(mender);

        return userGroupMapper.updateByPrimaryKey(ug) == 1;
    }

    @Override
    public UserGroupDto get(Long id) {
        Precondition.checkArgument(id != null && id > 0, "UserGroup id is invalid");

        final UserGroup ug = userGroupMapper.selectByPrimaryKey(id);
        Precondition.checkNotNull(ug, "UserGroup does not exist");

        return ConvertUtil.convert(ug);
    }

    @Override
    public UserGroupDto get(String name, String code, Long orgRootId) {
        Precondition.checkArgument(StringUtils.isNotBlank(name) || StringUtils.isNotBlank(code), "UserGroup name and code can not all be empty");
        Precondition.checkArgument(orgRootId != null && orgRootId > 0, "Root organization id is invalid");

        final UserGroup ug = userGroupMapper.selectByNameOrCodeAndOrgRootId(name, code, orgRootId);
        Precondition.checkNotNull(ug, "UserGroup does not exist");

        return ConvertUtil.convert(ug);
    }

    @Override
    public List<UserGroupPropertiesDto> getProperties(Long id) {
        Precondition.checkArgument(id != null && id > 0, "UserGroup id is invalid");

        final UserGroup org = userGroupMapper.selectByPrimaryKey(id);

        Precondition.checkNotNull(org, "UserGroup does not exist");

        final List<UserGroupProperties> properties = userGroupPropertiesMapper.selectByUgId(id);
        if (CollectionUtils.isEmpty(properties)) {
            return Collections.emptyList();
        }

        return properties.stream().map(ConvertUtil::convert).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean setProperties(Long id, String property, String value, Long mender) {
        Precondition.checkArgument(id != null && id > 0, "UserGroup id is invalid");
        Precondition.checkArgument(StringUtils.isNotBlank(property), "UserGroup can not be empty");

        final UserGroup org = userGroupMapper.selectByPrimaryKey(id);

        Precondition.checkNotNull(org, "UserGroup does not exist");

        final UserGroupProperties ugProperties = userGroupPropertiesMapper.selectByUgIdAndProperty(id, property);

        if (ugProperties == null) {
            final UserGroupProperties newProperties = new UserGroupProperties();
            newProperties.setUserGroupId(id);
            newProperties.setProperty(property);
            newProperties.setValue(value);
            newProperties.setCreator(mender);
            newProperties.setMender(mender);
            newProperties.setType(UserGroupPropTypeEnum.DEFAULT.getCode());
            newProperties.setStatus(UserGroupPropStatusEnum.DEFAULT.getCode());

            Precondition.checkArgument(userGroupPropertiesMapper.insert(newProperties) == 1, "Failed to add userGroup properties");
        } else {
            ugProperties.setValue(value);
            ugProperties.setMender(mender);

            Precondition.checkArgument(userGroupPropertiesMapper.updateByPrimaryKey(ugProperties) == 1, "Failed to update userGroup properties");
        }

        return true;
    }

    @Override
    public List<Long> getUsers(Long id) {
        Precondition.checkArgument(id != null && id > 0, "UserGroup id is invalid");
        Precondition.checkNotNull(userGroupMapper.selectByPrimaryKey(id), "UserGroup does not exist");

        final List<UserUgRelation> relations = userUgRelationMapper.selectByUgId(id);
        if (CollectionUtils.isEmpty(relations)) {
            return Collections.emptyList();
        }

        return relations.stream().map(UserUgRelation::getUserId).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addUser(Long id, Long userId, Long mender) {
        Precondition.checkArgument(id != null && id > 0, "UserGroup id is invalid");
        Precondition.checkArgument(userId != null && userId > 0, "User id is invalid");

        Precondition.checkNotNull(userGroupMapper.selectByPrimaryKey(id), "UserGroup does not exist");

        Precondition.checkNull(userUgRelationMapper.selectByUgIdAndUserId(id, userId), "UserGroup user already exists");
        final UserUgRelation newRelation = new UserUgRelation();
        newRelation.setUserGroupId(id);
        newRelation.setUserId(userId);
        newRelation.setCreator(mender);
        newRelation.setMender(mender);
        newRelation.setType(UserUgRelationTypeEnum.DEFAULT.getCode());
        newRelation.setStatus(UserUgRelationStatusEnum.DEFAULT.getCode());
        // todo think about expire time, whether need it
        newRelation.setExpireTime(DateUtil.maxDate());

        Precondition.checkArgument(userUgRelationMapper.insert(newRelation) == 1, "Failed to add userGroup user");
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeUser(Long id, Long userId, Long mender) {
        Precondition.checkArgument(id != null && id > 0, "UserGroup id is invalid");
        Precondition.checkArgument(userId != null && userId > 0, "User id is invalid");

        Precondition.checkNotNull(userGroupMapper.selectByPrimaryKey(id), "UserGroup does not exist");

        final UserUgRelation relation = userUgRelationMapper.selectByUgIdAndUserId(id, userId);
        Precondition.checkNotNull(relation, "UserGroup user does not exist");

        return userUgRelationMapper.deleteByPrimaryKey(relation.getId()) == 1;
    }
}
