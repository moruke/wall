package com.github.moruke.wall.auth.service.impl;

import com.github.moruke.wall.auth.core.IRbacIJudge;
import com.github.moruke.wall.auth.dao.entity.Object;
import com.github.moruke.wall.auth.dao.mapper.ObjectMapper;
import com.github.moruke.wall.auth.dtos.permission.ObjectDto;
import com.github.moruke.wall.auth.service.IObject;
import com.github.moruke.wall.auth.utils.ConvertUtil;
import com.github.moruke.wall.common.utils.Precondition;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

@Component
public class ObjectImpl implements IObject {

    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private IRbacIJudge rbacJudge;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long add(ObjectDto dto) {
        Precondition.checkNotNull(dto, "dto is null");

        // check if object already exists
        final Object obj = objectMapper.selectByNameAndDomain(dto.getName(), dto.getDomainDto().getId());
        Precondition.checkNull(obj, "object already exists");

        final Object object = ConvertUtil.convert(dto);

        objectMapper.insert(object);
        return object.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean remove(Long id) {
        Precondition.checkArgument(id != null && id > 0, "id must be greater than 0");
        return objectMapper.deleteByPrimaryKey(id) == 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(ObjectDto dto) {
        Precondition.checkNotNull(dto, "dto is null");

        Precondition.checkArgument(dto.getId() != null && dto.getId() > 0, "id must be greater than 0");

        // check if object already exists
        final Object obj = objectMapper.selectByNameAndDomain(dto.getName(), dto.getDomainDto().getId());
        if (obj != null && !obj.getId().equals(dto.getId())) {
            Precondition.checkNull(obj, "object already exists");
        }

        final Object object = ConvertUtil.convert(dto);

        return objectMapper.updateByPrimaryKeySelective(object) == 1;
    }

    @Override
    public ObjectDto get(Long id) {
        Precondition.checkArgument(id != null && id > 0, "id must be greater than 0");

        final Object object = objectMapper.selectByPrimaryKey(id);
        Precondition.checkNotNull(object, "object not found");

        return ConvertUtil.convert(object);
    }

    @Override
    public ObjectDto get(String name, Long domainId) {
        Precondition.checkArgument(domainId != null && domainId > 0, "domainId must be greater than 0");
        Precondition.checkArgument(StringUtils.isNotBlank(name), "name must not be empty");

        final Object object = objectMapper.selectByNameAndDomain(name, domainId);

        Precondition.checkNotNull(object, "object not found");
        return ConvertUtil.convert(object);
    }

    @Override
    public boolean updateParent(Long id, Long parentId) {
        Precondition.checkArgument(id != null && id > 0, "id must be greater than 0");

        // check if object exists

        final Object son = objectMapper.selectByPrimaryKey(id);
        Precondition.checkNotNull(son, "object not found");

        if (isNull(parentId)) {
            // remove parent
            Precondition.checkArgument(objectMapper.updateParent(id, null) == 1, "update parent failed");
            Precondition.checkArgument(rbacJudge.removeObjectParent(String.valueOf(id), String.valueOf(son.getParentId()), String.valueOf(son.getDomainId())), "remove object parent failed");
        } else {
            final Object parent = objectMapper.selectByPrimaryKey(parentId);
            Precondition.checkNotNull(parent, "parent object not found");
            Precondition.checkArgument(objectMapper.updateParent(id, parentId) == 1, "update parent failed");
            Precondition.checkArgument(rbacJudge.removeObjectParent(String.valueOf(id), String.valueOf(son.getParentId()), String.valueOf(son.getDomainId())), "remove object parent failed");
            Precondition.checkArgument(rbacJudge.addObjectParent(String.valueOf(id), String.valueOf(parentId), String.valueOf(son.getDomainId())), "add object parent failed");
        }
        return true;
    }

    @Override
    public ObjectDto getParent(Long id) {
        Precondition.checkArgument(id != null && id > 0, "id must be greater than 0");
        final Object object = requireNonNull(objectMapper.selectByPrimaryKey(id), "object not found");

        final Object parent = objectMapper.selectByPrimaryKey(object.getParentId());
        if (isNull(parent)) {
            return null;
        }
        return ConvertUtil.convert(parent);
    }

    @Override
    public List<ObjectDto> getChildren(Long id) {
        Precondition.checkArgument(id != null && id > 0, "id must be greater than 0");
        final List<Object> objects = objectMapper.selectByParentId(id);
        if (CollectionUtils.isEmpty(objects)) {
            return Collections.emptyList();
        }
        return objects.stream().map(ConvertUtil::convert).collect(Collectors.toList());
    }
}
