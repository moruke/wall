package com.github.moruke.wall.auth.service;

import com.github.moruke.wall.auth.dtos.permission.ActionDto;

public interface IAction {

    Long add(ActionDto dto);

    boolean remove(Long id);

    boolean update(ActionDto dto);

    ActionDto get(Long id);

    ActionDto get(String name, Long domainId);

}
