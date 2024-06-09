package com.github.moruke.wall.auth.service;

import com.github.moruke.wall.auth.dtos.permission.ObjectDto;

import java.util.List;

public interface IObject {

    Long add(ObjectDto dto);

    boolean remove(Long id);

    boolean update(ObjectDto dto);

    ObjectDto get(Long id);

    ObjectDto get(String name, Long domainId);

    boolean updateParent(Long id, Long parentId);

    ObjectDto getParent(Long id);

    List<ObjectDto> getChildren(Long id);

}
