package com.github.moruke.wall.auth.service.impl;

import com.github.moruke.wall.auth.dao.entity.Action;
import com.github.moruke.wall.auth.dao.mapper.ActionMapper;
import com.github.moruke.wall.auth.dtos.permission.ActionDto;
import com.github.moruke.wall.auth.service.IAction;
import com.github.moruke.wall.auth.utils.ConvertUtil;
import com.github.moruke.wall.common.utils.Precondition;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Component
public class ActionImpl implements IAction {

    @Resource
    private ActionMapper actionMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long add(ActionDto dto) {
        Precondition.checkNotNull(dto, "dto is null");

        // check if action already exists
        final Action act = actionMapper.selectByNameAndDomain(dto.getName(), dto.getDomainDto().getId());
        Precondition.checkNull(act, "action already exists");

        final Action action = ConvertUtil.convert(dto);

        actionMapper.insert(action);

        return action.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean remove(Long id) {
        Precondition.checkArgument(id != null && id > 0, "id must be greater than 0");

        return actionMapper.deleteByPrimaryKey(id) == 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(ActionDto actionDto) {
        Precondition.checkNotNull(actionDto, "actionDto is null");
        Precondition.checkArgument(actionDto.getId() != null && actionDto.getId() > 0, "id must be greater than 0");

        // check if action already exists

        final Action act = actionMapper.selectByNameAndDomain(actionDto.getName(), actionDto.getDomainDto().getId());
        if (act != null && !act.getId().equals(actionDto.getId())) {
            Precondition.checkNull(act, "action already exists");
        }

        final Action action = ConvertUtil.convert(actionDto);

        return actionMapper.updateByPrimaryKey(action) == 1;
    }

    @Override
    public ActionDto get(Long id) {
        Precondition.checkArgument(id != null && id > 0, "id must be greater than 0");

        final Action action = actionMapper.selectByPrimaryKey(id);
        Precondition.checkNotNull(action, "action not found");

        return ConvertUtil.convert(action);
    }

    @Override
    public ActionDto get(String name, Long domainId) {
        Precondition.checkArgument(StringUtils.isNotBlank(name), "name is null or empty");
        Precondition.checkArgument(domainId != null && domainId > 0, "domainId must be greater than 0");

        final Action action = actionMapper.selectByNameAndDomain(name, domainId);
        Precondition.checkNotNull(action, "action not found");

        return ConvertUtil.convert(action);
    }
}
