package com.github.moruke.wall.account.service.impl;

import com.github.moruke.wall.account.dao.entity.*;
import com.github.moruke.wall.account.dao.mapper.*;
import com.github.moruke.wall.account.dto.UserDto;
import com.github.moruke.wall.account.dto.UserPropertiesDto;
import com.github.moruke.wall.account.enums.*;
import com.github.moruke.wall.account.service.IUser;
import com.github.moruke.wall.account.utils.ConvertUtil;
import com.github.moruke.wall.common.utils.NameUtil;
import com.github.moruke.wall.common.utils.Precondition;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class UserImpl implements IUser {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private UserPropertiesMapper userPropertiesMapper;

    @Resource
    private UserUgRelationMapper userUgRelationMapper;

    @Resource
    private UserOrgRelationMapper userOrgRelationMapper;

    @Value("${wall.rules.user.name-length}")
    private int nameLength;

    @Value("#{'${wall.rules.user.name-rules}'.split(',')}")
    private List<String> nameRules;

    @Value("${wall.rules.user.salt-length}")
    private int saltLength;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long add(UserDto dto) {
        Precondition.checkArgument(NameUtil.checkLength(dto.getName(), nameLength), "User name is too long");
        Precondition.checkArgument(NameUtil.checkRule(dto.getName(), nameRules), "User name does not meet the rules");
        Precondition.checkArgument(NameUtil.checkRule(dto.getNickName(), nameRules), "User name does not meet the rules");

        final User user = ConvertUtil.convert(dto);
        final UserInfo userInfo = ConvertUtil.convertToInfo(dto);

        user.setCode(UUID.randomUUID().toString());
        user.setSourceType(SourceTypeEnum.DEFAULT.getCode());

        Precondition.checkNull(userMapper.selectByNameAndOrgRootId(dto.getName(), dto.getOrgRootId()), "User name already exists");

        Precondition.checkArgument(userMapper.insert(user) == 1, "User add failed");

        userInfo.setUserId(user.getId());

        Precondition.checkArgument(userInfoMapper.insert(userInfo) == 1, "User info add failed");

        return user.getId();

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean remove(Long id, Long mender) {
        Precondition.checkNotNull(userMapper.selectByPrimaryKey(id), "User does not exist");

        final List<UserUgRelation> userUgRelations = userUgRelationMapper.selectByUserId(id);
        final List<UserOrgRelation> userOrgRelations = userOrgRelationMapper.selectByUserId(id);

        if (CollectionUtils.isEmpty(userUgRelations) && CollectionUtils.isEmpty(userOrgRelations)) {
            Precondition.checkArgument(userMapper.deleteByPrimaryKey(id) == 1, "User delete failed");
            Precondition.checkArgument(userInfoMapper.deleteByUserId(id) == 1, "User info delete failed");
            // todo insert userHistory
        } else {
            Precondition.checkArgument(CollectionUtils.isEmpty(userUgRelations), "User has userGroup");
            Precondition.checkArgument(CollectionUtils.isEmpty(userOrgRelations), "User has organization");
        }

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateType(Long id, Byte type, Long mender) {
        Precondition.checkArgument(id != null && id > 0, "User id is invalid");

        final User user = userMapper.selectByPrimaryKey(id);
        Precondition.checkNotNull(user, "User does not exist");

        user.setType(UserTypeEnum.find(type).getCode());
        user.setMender(mender);

        return userMapper.updateByPrimaryKey(user) == 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateStatus(Long id, Byte status, Long mender) {
        Precondition.checkArgument(id != null && id > 0, "User id is invalid");

        final User user = userMapper.selectByPrimaryKey(id);
        Precondition.checkNotNull(user, "User does not exist");

        user.setStatus(UserStatusEnum.find(status).getCode());
        user.setMender(mender);

        return userMapper.updateByPrimaryKey(user) == 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateInfo(UserDto dto) {
        Precondition.checkArgument(dto.getId() != null && dto.getId() > 0, "User id is invalid");
        Precondition.checkArgument(NameUtil.checkRule(dto.getNickName(), nameRules), "User nickname does not meet the rules");

        Precondition.checkNotNull(userMapper.selectByPrimaryKey(dto.getId()), "User does not exist");

        final UserInfo updateUserInfo = ConvertUtil.convertToInfo(dto);

        final UserInfo userInfo = userInfoMapper.selectByUserId(dto.getId());
        if (userInfo == null) {
            Precondition.checkArgument(userInfoMapper.insert(updateUserInfo) == 1, "User info add failed");
        } else {
            updateUserInfo.setId(userInfo.getId());
            Precondition.checkArgument(userInfoMapper.updateByPrimaryKey(updateUserInfo) == 1, "User info update failed");
        }

        return true;
    }

    @Override
    public UserDto get(Long id) {
        Precondition.checkArgument(id != null && id > 0, "User id is invalid");
        final User user = userMapper.selectByPrimaryKey(id);
        Precondition.checkNotNull(user, "User does not exist");

        final UserInfo userInfo = userInfoMapper.selectByUserId(id);
        Precondition.checkNotNull(userInfo, "User info does not exist");

        return ConvertUtil.convertToDto(user, userInfo);
    }

    @Override
    public UserDto get(String name, String code, Long orgRootId) {
        Precondition.checkArgument(StringUtils.isNotBlank(name) || StringUtils.isNotBlank(code), "User name and code can not all be empty");
        Precondition.checkArgument(orgRootId != null && orgRootId > 0, "Root organization id is invalid");

        final User user = userMapper.selectByNameOrCodeAndOrgRootId(name, code, orgRootId);
        Precondition.checkNotNull(user, "User does not exist");

        final UserInfo userInfo = userInfoMapper.selectByUserId(user.getId());
        Precondition.checkNotNull(userInfo, "User info does not exist");

        return ConvertUtil.convertToDto(user, userInfo);
    }

    @Override
    public List<UserPropertiesDto> getProperties(Long id) {
        Precondition.checkArgument(id != null && id > 0, "User id is invalid");

        final List<UserProperties> userProperties = userPropertiesMapper.selectByUserId(id);
        if (CollectionUtils.isEmpty(userProperties)) {
            return Collections.emptyList();
        }

        return userProperties.stream().map(ConvertUtil::convert).collect(Collectors.toList());
    }

    @Override
    public UserPropertiesDto getProperty(Long id, String property) {
        Precondition.checkArgument(id != null && id > 0, "User id is invalid");
        Precondition.checkArgument(StringUtils.isNotBlank(property), "User property is invalid");

        final UserProperties userProperties = userPropertiesMapper.selectByUserIdAndProperty(id, property);
        if (Objects.isNull(userProperties)) {
            return null;
        }

        return ConvertUtil.convert(userProperties);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean setProperties(Long id, String property, String value, Long mender) {
        Precondition.checkArgument(id != null && id > 0, "User id is invalid");
        Precondition.checkArgument(StringUtils.isNotBlank(property), "User property is invalid");

        final UserProperties userProperties = userPropertiesMapper.selectByUserIdAndProperty(id, property);
        if (userProperties == null) {
            final UserProperties insert = new UserProperties();
            insert.setUserId(id);
            insert.setProperty(property);
            insert.setValue(value);
            insert.setCreator(mender);
            insert.setMender(mender);
            insert.setType(UserPropTypeEnum.DEFAULT.getCode());
            insert.setStatus(UserPropStatusEnum.DEFAULT.getCode());

            return userPropertiesMapper.insert(insert) == 1;
        } else {
            userProperties.setValue(value);
            userProperties.setMender(mender);

            return userPropertiesMapper.updateByPrimaryKey(userProperties) == 1;
        }
    }

    @Override
    public List<Long> getUgIds(Long id) {
        final List<UserUgRelation> relations = userUgRelationMapper.selectByUserId(id);
        if (CollectionUtils.isEmpty(relations)) {
            return Collections.emptyList();
        }

        return relations.stream().map(UserUgRelation::getUserGroupId).collect(Collectors.toList());
    }

    @Override
    public List<Long> getOrgIds(Long id) {
        final List<UserOrgRelation> relations = userOrgRelationMapper.selectByUserId(id);
        if (CollectionUtils.isEmpty(relations)) {
            return Collections.emptyList();
        }

        return relations.stream().map(UserOrgRelation::getOrgId).collect(Collectors.toList());
    }

    @Override
    public String salt(Long id) {
        // generate salt by user basic info
        Precondition.checkArgument(id != null && id > 0, "User id is invalid");

        final User user = userMapper.selectByPrimaryKey(id);
        Precondition.checkNotNull(user, "User does not exist");

        final String name = user.getName();
        final String code = user.getCode();
        final Long orgRootId = user.getOrgRootId();
        final Date createTime = user.getCreateTime();

        return RandomStringUtils.random(saltLength, (name + code + orgRootId + createTime.getTime()));
    }
}
