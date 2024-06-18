package com.github.moruke.wall.account.service.impl;

import com.github.moruke.wall.account.dao.entity.*;
import com.github.moruke.wall.account.dao.mapper.*;
import com.github.moruke.wall.account.dto.OrgPropertiesDto;
import com.github.moruke.wall.account.dto.OrganizationDto;
import com.github.moruke.wall.account.enums.*;
import com.github.moruke.wall.account.service.IOrganization;
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
import java.util.*;
import java.util.stream.Collectors;

@Component
public class OrganizationImpl implements IOrganization {

    @Resource
    private OrganizationMapper organizationMapper;

    @Resource
    private OrganizationInfoMapper organizationInfoMapper;

    @Resource
    private OrganizationPropertiesMapper organizationPropertiesMapper;

    @Resource
    private UserOrgRelationMapper userOrgRelationMapper;

    @Resource
    private UgOrgRelationMapper ugOrgRelationMapper;

    @Value("${wall.rules.organization.name-length}")
    private int nameLength;

    @Value("#{'${wall.rules.organization.name-rules}'.split(',')}")
    private List<String> nameRules;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long add(OrganizationDto dto) {

        Precondition.checkArgument(NameUtil.checkLength(dto.getName(), nameLength), "Organization name is too long");
        Precondition.checkArgument(NameUtil.checkRule(dto.getName(), nameRules), "Organization name does not meet the rules");
        Precondition.checkArgument(NameUtil.checkRule(dto.getNickName(), nameRules), "Organization name does not meet the rules");

        final Organization org = ConvertUtil.convert(dto);
        final OrganizationInfo orgInfo = ConvertUtil.convertToInfo(dto);

        // org code is uuid

        org.setCode(UUID.randomUUID().toString());

        if (dto.getParentId() != null) {
            final Organization parent = organizationMapper.selectByPrimaryKey(dto.getParentId());
            Precondition.checkNotNull(parent, "Parent organization does not exist");

            // use parent root as current root
            if (parent.getRootId() != null) {
                org.setRootId(parent.getRootId());
            } else {
                org.setRootId(parent.getId());
            }
        }

        if (dto.getRootId() != null) {
            final Organization root = organizationMapper.selectByPrimaryKey(dto.getRootId());
            Precondition.checkNotNull(root, "Root organization does not exist");
            Precondition.checkArgument(!root.getName().equals(dto.getName()), "Organization name already exists");

            if (dto.getParentId() == null) {
                org.setParentId(dto.getRootId());
            }
        }

        Precondition.checkNull(organizationMapper.selectByNameAndRootId(org.getName(), org.getRootId()), "Organization name already exists");

        Precondition.checkArgument(organizationMapper.insert(org) == 1, "Failed to add organization");

        orgInfo.setOrgId(org.getId());

        Precondition.checkArgument(organizationInfoMapper.insert(orgInfo) == 1, "Failed to add organization info");

        return org.getId();

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean remove(Long id, Long mender) {
        // check has children
        final List<Organization> children = organizationMapper.selectByParentId(id);
        Precondition.checkArgument(CollectionUtils.isEmpty(children), "Organization has children");

        final List<UserOrgRelation> userOrgRelations = userOrgRelationMapper.selectByOrgId(id);
        Precondition.checkArgument(CollectionUtils.isEmpty(userOrgRelations), "Organization has users");
        final List<UgOrgRelation> ugOrgRelations = ugOrgRelationMapper.selectByOrgId(id);
        Precondition.checkArgument(CollectionUtils.isEmpty(ugOrgRelations), "Organization has user groups");

        final Organization organization = organizationMapper.selectByPrimaryKey(id);
        Precondition.checkNotNull(organization, "Organization does not exist");
        Precondition.checkNotNull(organization.getRootId(), "Root organization can not be deleted");

        // todo mark delete
        Precondition.checkArgument(organizationMapper.deleteByPrimaryKey(id) == 1, "Failed to delete organization");
        Precondition.checkArgument(organizationInfoMapper.deleteByOrgId(id) == 1, "Failed to delete organization info");

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateType(Long id, Byte type, Long mender) {
        Precondition.checkArgument(id != null && id > 0, "Organization id is invalid");

        final Organization org = organizationMapper.selectByPrimaryKey(id);
        Precondition.checkNotNull(org, "Organization does not exist");

        org.setType(OrgTypeEnum.find(type).getCode());
        org.setMender(mender);

        Precondition.checkArgument(organizationMapper.updateByPrimaryKey(org) == 1, "Failed to update organization type");

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateStatus(Long id, Byte status, Long mender) {
        Precondition.checkArgument(id != null && id > 0, "Organization id is invalid");

        final Organization org = organizationMapper.selectByPrimaryKey(id);
        Precondition.checkNotNull(org, "Organization does not exist");

        org.setStatus(OrgStatusEnum.find(status).getCode());

        Precondition.checkArgument(organizationMapper.updateByPrimaryKey(org) == 1, "Failed to update organization status");

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateParent(Long id, Long parentId, Long mender) {
        Precondition.checkArgument(id != null && id > 0, "Organization id is invalid");
        Precondition.checkArgument(!Objects.equals(id, parentId), "Organization can not be parent of itself");

        final Organization org = organizationMapper.selectByPrimaryKey(id);

        if (parentId != null) {
            // check parent exist
            final Organization parent = organizationMapper.selectByPrimaryKey(parentId);
            Precondition.checkNotNull(parent, "Parent organization does not exist");

            // check is root
            if (org.getRootId() == null) {
                // root organization can not have parent
                throw new IllegalArgumentException("Root organization can not have parent");
            }
        } else {
            // check is root
            if (org.getRootId() != null) {
                // no root organization must have parent
                throw new IllegalArgumentException("No root organization must have parent");
            }
        }

        org.setParentId(parentId);
        org.setMender(mender);

        Precondition.checkArgument(organizationMapper.updateByPrimaryKey(org) == 1, "Failed to update organization parent");

        return true;
    }

    @Override
    public boolean updateInfo(OrganizationDto dto) {

        Precondition.checkArgument(dto.getId() != null && dto.getId() > 0, "Organization id is invalid");
        Precondition.checkArgument(NameUtil.checkRule(dto.getNickName(), nameRules), "Organization nickname does not meet the rules");

        // check exist
        final Organization org = organizationMapper.selectByPrimaryKey(dto.getId());
        Precondition.checkNotNull(org, "Organization does not exist");

        final OrganizationInfo orgInfo = organizationInfoMapper.selectByOrgId(dto.getId());

        final OrganizationInfo updateOrgInfo = ConvertUtil.convertToInfo(dto);

        Precondition.checkArgument(orgInfo != null, "Organization info does not exist");

        updateOrgInfo.setId(orgInfo.getId());
        Precondition.checkArgument(organizationInfoMapper.updateByPrimaryKey(updateOrgInfo) == 1, "Failed to update organization info");

        return true;
    }

    @Override
    public OrganizationDto get(Long id) {
        Precondition.checkArgument(id != null && id > 0, "Organization id is invalid");

        final Organization org = organizationMapper.selectByPrimaryKey(id);

        Precondition.checkNotNull(org, "Organization does not exist");

        final OrganizationInfo orgInfo = organizationInfoMapper.selectByOrgId(id);

        Precondition.checkNotNull(orgInfo, "Organization info does not exist");

        return ConvertUtil.convertToDto(org, orgInfo);
    }

    @Override
    public OrganizationDto get(String name, String code, Long rootId) {
        Precondition.checkArgument(StringUtils.isNotBlank(name) || StringUtils.isNotBlank(code), "Organization name and code can not all be empty");

        // if rootId is null, it's root organization
        final Organization org = organizationMapper.selectByNameOrCodeAndRootId(name, code, rootId);

        Precondition.checkNotNull(org, "Organization does not exist");

        final OrganizationInfo orgInfo = organizationInfoMapper.selectByOrgId(org.getId());

        Precondition.checkNotNull(orgInfo, "Organization info does not exist");

        return ConvertUtil.convertToDto(org, orgInfo);

    }

    @Override
    public List<OrganizationDto> getChildren(Long parentId) {
        Precondition.checkArgument(parentId != null && parentId > 0, "Organization id is invalid");

        final List<Organization> orgs = organizationMapper.selectByParentId(parentId);

        if (CollectionUtils.isEmpty(orgs)) {
            return Collections.emptyList();
        }

        // only return basic info
        return orgs.stream().map(o -> ConvertUtil.convertToDto(o, null)).collect(Collectors.toList());
    }

    @Override
    public OrganizationDto getRoot(Long id) {
        Precondition.checkArgument(id != null && id > 0, "Organization id is invalid");

        final Organization org = organizationMapper.selectByPrimaryKey(id);

        Precondition.checkNotNull(org, "Organization does not exist");

        if (org.getRootId() == null) {
            return ConvertUtil.convertToDto(org, null);
        }

        final Organization root = organizationMapper.selectByPrimaryKey(org.getRootId());

        Precondition.checkNotNull(root, "Root organization does not exist");

        return ConvertUtil.convertToDto(root, null);
    }

    @Override
    public OrganizationDto getParent(Long id) {
        Precondition.checkArgument(id != null && id > 0, "Organization id is invalid");

        final Organization org = organizationMapper.selectByPrimaryKey(id);

        Precondition.checkNotNull(org, "Organization does not exist");

        if (org.getParentId() == null) {
            throw new IllegalArgumentException("No parent organization");
        }

        final Organization parent = organizationMapper.selectByPrimaryKey(org.getParentId());

        Precondition.checkNotNull(parent, "Parent organization does not exist");

        return ConvertUtil.convertToDto(parent, null);
    }

    @Override
    public List<OrgPropertiesDto> getProperties(Long id) {
        final List<OrganizationProperties> properties = organizationPropertiesMapper.selectByOrgId(id);
        if (CollectionUtils.isEmpty(properties)) {
            return Collections.emptyList();
        }

        return properties.stream().map(ConvertUtil::convert).collect(Collectors.toList());

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean setProperties(Long id, String property, String value, boolean overrideChildren, Long mender) {
        Precondition.checkArgument(id != null && id > 0, "Organization id is invalid");
        Precondition.checkArgument(StringUtils.isNotBlank(property), "Property can not be empty");

        final Organization org = organizationMapper.selectByPrimaryKey(id);

        Precondition.checkNotNull(org, "Organization does not exist");

        final OrganizationProperties orgProperties = organizationPropertiesMapper.selectByOrgIdAndProperty(id, property);

        if (orgProperties == null) {
            final OrganizationProperties newProperties = new OrganizationProperties();
            newProperties.setOrgId(id);
            newProperties.setProperty(property);
            newProperties.setValue(value);
            newProperties.setCreator(mender);
            newProperties.setMender(mender);
            newProperties.setType(OrgPropTypeEnum.DEFAULT.getCode());
            newProperties.setStatus(OrgPropStatusEnum.DEFAULT.getCode());

            Precondition.checkArgument(organizationPropertiesMapper.insert(newProperties) == 1, "Failed to add organization properties");
        } else {
            orgProperties.setValue(value);
            orgProperties.setMender(mender);

            Precondition.checkArgument(organizationPropertiesMapper.updateByPrimaryKey(orgProperties) == 1, "Failed to update organization properties");
        }

        if (overrideChildren) {
            final List<Organization> children = getAllChildren(id);
            if (CollectionUtils.isNotEmpty(children)) {
                final List<Long> childIds = children.stream().map(Organization::getId).collect(Collectors.toList());
                final List<OrganizationProperties> childProperties = organizationPropertiesMapper.selectByOrgIdsAndProperty(childIds, property);

                if (CollectionUtils.isNotEmpty(childProperties)) {
                    final Map<Long, OrganizationProperties> childPropertiesMap = childProperties.stream().collect(Collectors.toMap(OrganizationProperties::getOrgId, p -> p));

                    final List<OrganizationProperties> insert = new ArrayList<>();
                    final List<OrganizationProperties> update = new ArrayList<>();

                    for (Organization child : children) {
                        final OrganizationProperties childProperty = childPropertiesMap.get(child.getId());
                        if (childProperty == null) {
                            final OrganizationProperties newProperties = new OrganizationProperties();
                            newProperties.setOrgId(child.getId());
                            newProperties.setProperty(property);
                            newProperties.setValue(value);
                            newProperties.setCreator(mender);
                            newProperties.setMender(mender);

                            newProperties.setType(OrgPropTypeEnum.DEFAULT.getCode());
                            newProperties.setStatus(OrgPropStatusEnum.DEFAULT.getCode());

                            insert.add(newProperties);
                        } else {
                            childProperty.setValue(value);
                            childProperty.setMender(mender);

                            childProperty.setType(OrgPropTypeEnum.DEFAULT.getCode());
                            childProperty.setStatus(OrgPropStatusEnum.DEFAULT.getCode());

                            update.add(childProperty);
                        }
                    }

                    if (CollectionUtils.isNotEmpty(insert)) {
                        Precondition.checkArgument(organizationPropertiesMapper.batchInsert(insert) == insert.size(), "Failed to add organization properties");
                    }

                    if (CollectionUtils.isNotEmpty(update)) {
                        // not support batch update, use deleteBatchInsert instead of update one by one
                        Precondition.checkArgument(organizationPropertiesMapper.batchDelete(update.stream().map(OrganizationProperties::getId).collect(Collectors.toList())) == update.size(), "Failed to delete organization properties");
                        Precondition.checkArgument(organizationPropertiesMapper.batchInsert(update) == update.size(), "Failed to update organization properties");
                    }
                } else {
                    final List<OrganizationProperties> insert = new ArrayList<>();
                    for (Organization child : children) {
                        final OrganizationProperties newProperties = new OrganizationProperties();
                        newProperties.setOrgId(child.getId());
                        newProperties.setProperty(property);
                        newProperties.setValue(value);
                        newProperties.setCreator(mender);
                        newProperties.setMender(mender);
                        newProperties.setType(OrgPropTypeEnum.DEFAULT.getCode());
                        newProperties.setStatus(OrgPropStatusEnum.DEFAULT.getCode());

                        insert.add(newProperties);
                    }

                    Precondition.checkArgument(organizationPropertiesMapper.batchInsert(insert) == insert.size(), "Failed to add organization properties");
                }
            }
        }

        return true;
    }

    @Override
    public List<Long> getUsers(Long id) {
        Precondition.checkArgument(id != null && id > 0, "Organization id is invalid");

        Precondition.checkNotNull(organizationMapper.selectByPrimaryKey(id), "Organization does not exist");

        final List<UserOrgRelation> relations = userOrgRelationMapper.selectByOrgId(id);

        if (CollectionUtils.isEmpty(relations)) {
            return Collections.emptyList();
        }

        return relations.stream().map(UserOrgRelation::getUserId).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addUser(Long id, Long userId, Long mender) {
        Precondition.checkArgument(id != null && id > 0, "Organization id is invalid");

        Precondition.checkArgument(userId != null && userId > 0, "User id is invalid");

        Precondition.checkNotNull(organizationMapper.selectByPrimaryKey(id), "Organization does not exist");

        Precondition.checkNull(userOrgRelationMapper.selectByOrgIdAndUserId(id, userId), "User already exists in the organization");

        final UserOrgRelation relation = new UserOrgRelation();
        relation.setOrgId(id);
        relation.setType(UserOrgRelationTypeEnum.DEFAULT.getCode());
        relation.setStatus(UserOrgRelationStatusEnum.DEFAULT.getCode());
        relation.setUserId(userId);
        relation.setCreator(mender);
        relation.setMender(mender);

        Precondition.checkArgument(userOrgRelationMapper.insert(relation) == 1, "Failed to add user to organization");

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeUser(Long id, Long userId, Long mender) {
        Precondition.checkArgument(id != null && id > 0, "Organization id is invalid");

        Precondition.checkArgument(userId != null && userId > 0, "User id is invalid");

        Precondition.checkNotNull(organizationMapper.selectByPrimaryKey(id), "Organization does not exist");

        final UserOrgRelation relation = userOrgRelationMapper.selectByOrgIdAndUserId(id, userId);

        Precondition.checkNotNull(relation, "User does not exist in the organization");

        Precondition.checkArgument(userOrgRelationMapper.deleteByPrimaryKey(relation.getId()) == 1, "Failed to remove user from organization");

        return true;
    }

    @Override
    public List<Long> getUserGroups(Long id) {
        Precondition.checkArgument(id != null && id > 0, "Organization id is invalid");

        Precondition.checkNotNull(organizationMapper.selectByPrimaryKey(id), "Organization does not exist");

        final List<UgOrgRelation> relations = ugOrgRelationMapper.selectByOrgId(id);

        if (CollectionUtils.isEmpty(relations)) {
            return Collections.emptyList();
        }

        return relations.stream().map(UgOrgRelation::getUserGroupId).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addUserGroup(Long id, Long userGroupId, Long mender) {
        Precondition.checkArgument(id != null && id > 0, "Organization id is invalid");

        Precondition.checkArgument(userGroupId != null && userGroupId > 0, "User group id is invalid");

        Precondition.checkNotNull(organizationMapper.selectByPrimaryKey(id), "Organization does not exist");

        Precondition.checkNull(ugOrgRelationMapper.selectByOrgIdAndUserGroupId(id, userGroupId), "User group already exists in the organization");

        final UgOrgRelation relation = new UgOrgRelation();
        relation.setOrgId(id);
        relation.setUserGroupId(userGroupId);
        relation.setType(UgOrgRelationTypeEnum.DEFAULT.getCode());
        relation.setStatus(UgOrgRelationStatusEnum.DEFAULT.getCode());
        relation.setCreator(mender);
        relation.setMender(mender);
        // todo think about expire time, whether need it
        relation.setExpireTime(DateUtil.maxDate());

        Precondition.checkArgument(ugOrgRelationMapper.insert(relation) == 1, "Failed to add user group to organization");

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeUserGroup(Long id, Long userGroupId, Long mender) {
        Precondition.checkArgument(id != null && id > 0, "Organization id is invalid");

        Precondition.checkArgument(userGroupId != null && userGroupId > 0, "User group id is invalid");

        Precondition.checkNotNull(organizationMapper.selectByPrimaryKey(id), "Organization does not exist");

        final UgOrgRelation relation = ugOrgRelationMapper.selectByOrgIdAndUserGroupId(id, userGroupId);

        Precondition.checkNotNull(relation, "User group does not exist in the organization");

        Precondition.checkArgument(ugOrgRelationMapper.deleteByPrimaryKey(relation.getId()) == 1, "Failed to remove user group from organization");

        return true;
    }

    @Override
    public OrganizationDto getRootOrgByName(String rootOrgName) {
        Precondition.checkArgument(StringUtils.isNotBlank(rootOrgName), "Root organization name can not be empty");

        final Organization org = organizationMapper.selectByNameAndRootId(rootOrgName, null);

        Precondition.checkNotNull(org, "Root organization does not exist");

        return ConvertUtil.convertToDto(org, null);
    }

    @Override
    public boolean checkValid(Long orgId, Long rootId) {
        Precondition.checkArgument(orgId != null && orgId > 0, "Organization id is invalid");

        if (rootId == null) {
            return checkValid(orgId);
        } else {
            return checkValid(orgId) && checkValid(rootId);
        }
    }

    private boolean checkValid(Long orgId) {
        return OrgStatusEnum.find(organizationMapper.selectByPrimaryKey(orgId).getStatus()).valid();
    }

    private List<Organization> getAllChildren(Long id) {
        final List<Organization> children = organizationMapper.selectByParentId(id);
        if (CollectionUtils.isEmpty(children)) {
            return Collections.emptyList();
        }

        List<Organization> allChildren = new ArrayList<>(children);
        for (Organization child : children) {
            final List<Organization> grandChildren = getAllChildren(child.getId());
            if (CollectionUtils.isNotEmpty(grandChildren)) {
                allChildren.addAll(grandChildren);
            }
        }

        return allChildren;
    }
}
