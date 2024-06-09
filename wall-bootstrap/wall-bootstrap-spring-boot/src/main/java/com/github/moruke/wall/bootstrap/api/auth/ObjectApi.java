package com.github.moruke.wall.bootstrap.api.auth;

import com.github.moruke.wall.auth.dtos.permission.ObjectDto;
import com.github.moruke.wall.auth.service.IObject;
import com.github.moruke.wall.common.dto.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/wall/auth")
@Api(tags = "Object")
public class ObjectApi {
    @Resource
    private IObject objectImpl;

    @PutMapping("object")
    @ApiOperation(value = "add object", httpMethod = "PUT")
    public Result<Long> add(@RequestBody ObjectDto dto) {
        return Result.success(objectImpl.add(dto));
    }

    @DeleteMapping("object/{id}")
    @ApiOperation(value = "remove object", httpMethod = "DELETE")
    public Result<Boolean> remove(@PathVariable Long id) {
        return Result.success(objectImpl.remove(id));
    }

    @PostMapping("object")
    @ApiOperation(value = "update object", httpMethod = "POST")
    public Result<Boolean> update(@RequestBody ObjectDto dto) {
        return Result.success(objectImpl.update(dto));
    }

    @GetMapping("object/{id}")
    @ApiOperation(value = "get object", httpMethod = "GET")
    public Result<ObjectDto> get(@PathVariable Long id) {
        return Result.success(objectImpl.get(id));
    }

    @GetMapping("object/{domainId}/{name}")
    @ApiOperation(value = "get object", httpMethod = "GET")
    public Result<ObjectDto> get(@PathVariable String name, @PathVariable Long domainId) {
        return Result.success(objectImpl.get(name, domainId));
    }

    @PostMapping("object/parent/{id}/{parentId}")
    @ApiOperation(value = "update object parent", httpMethod = "POST")
    public Result<Boolean> updateParent(@PathVariable Long id, @PathVariable Long parentId) {
        return Result.success(objectImpl.updateParent(id, parentId));
    }

    @GetMapping("object/parent/{id}")
    @ApiOperation(value = "get object parent", httpMethod = "GET")
    public Result<ObjectDto> getParent(@PathVariable Long id) {
        return Result.success(objectImpl.getParent(id));
    }

    @GetMapping("object/children/{id}")
    @ApiOperation(value = "get object children", httpMethod = "GET")
    public Result<List<ObjectDto>> getChildren(@PathVariable Long id) {
        return Result.success(objectImpl.getChildren(id));
    }
}
