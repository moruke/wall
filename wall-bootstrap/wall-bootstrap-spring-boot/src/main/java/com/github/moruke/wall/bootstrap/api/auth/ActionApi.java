package com.github.moruke.wall.bootstrap.api.auth;

import com.github.moruke.wall.auth.dtos.permission.ActionDto;
import com.github.moruke.wall.auth.service.IAction;
import com.github.moruke.wall.common.dto.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/wall/auth")
@Api(tags = "Action")
public class ActionApi {
    @Resource
    private IAction actionImpl;

    @PutMapping("action")
    @ApiOperation(value = "add action", httpMethod = "PUT")
    public Result<Long> add(@RequestBody ActionDto dto) {
        return Result.success(actionImpl.add(dto));
    }

    @DeleteMapping("action/{id}")
    @ApiOperation(value = "remove action", httpMethod = "DELETE")
    public Result<Boolean> remove(@PathVariable Long id) {
        return Result.success(actionImpl.remove(id));
    }

    @PostMapping("action")
    @ApiOperation(value = "update action", httpMethod = "POST")
    public Result<Boolean> update(@RequestBody ActionDto dto) {
        return Result.success(actionImpl.update(dto));
    }

    @GetMapping("action/{id}")
    @ApiOperation(value = "get action", httpMethod = "GET")
    public Result<ActionDto> get(@PathVariable Long id) {
        return Result.success(actionImpl.get(id));
    }

    @GetMapping("action/{domainId}/{name}")
    @ApiOperation(value = "get action", httpMethod = "GET")
    public Result<ActionDto> get(@PathVariable String name, @PathVariable Long domainId) {
        return Result.success(actionImpl.get(name, domainId));
    }
}
