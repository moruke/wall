package com.github.moruke.wall.bootstrap.api.account;

import com.github.moruke.wall.account.dto.UserGroupDto;
import com.github.moruke.wall.account.dto.UserGroupPropertiesDto;
import com.github.moruke.wall.account.service.IUserGroup;
import com.github.moruke.wall.common.dto.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 狄杰
 * @date 2024/6/11
 * @description
 */
@RestController
@RequestMapping("/wall/account")
@Api(tags = "UserGroup")
public class UserGroupApi {
    @Resource
    private IUserGroup userGroupImpl;

    @PutMapping("userGroup")
    @ApiOperation(value = "add user group", httpMethod = "PUT")
    public Result<Long> add(@RequestBody UserGroupDto dto) {
        return Result.success(userGroupImpl.add(dto));
    }

    @DeleteMapping("userGroup/{id}")
    @ApiOperation(value = "remove user group", httpMethod = "DELETE")
    public Result<Boolean> remove(@PathVariable Long id, @RequestHeader(name = "userId") Long mender) {
        return Result.success(userGroupImpl.remove(id, mender));
    }

    @PostMapping("userGroup/type")
    @ApiOperation(value = "update user group type", httpMethod = "POST")
    public Result<Boolean> updateType(@RequestParam Long id, @RequestParam Byte type, @RequestHeader(name = "userId") Long mender) {
        return Result.success(userGroupImpl.updateType(id, type, mender));
    }

    @PostMapping("userGroup/status")
    @ApiOperation(value = "update user group status", httpMethod = "POST")
    public Result<Boolean> updateStatus(@RequestParam Long id, @RequestParam Byte status, @RequestHeader(name = "userId") Long mender) {
        return Result.success(userGroupImpl.updateStatus(id, status, mender));
    }

    @GetMapping("userGroup/{id}")
    @ApiOperation(value = "get user group", httpMethod = "GET")
    public Result<UserGroupDto> get(@PathVariable Long id) {
        return Result.success(userGroupImpl.get(id));
    }

    @GetMapping("userGroup")
    @ApiOperation(value = "get user group", httpMethod = "GET")
    public Result<UserGroupDto> get(@RequestParam String name, @RequestParam String code, @RequestParam Long orgRootId) {
        return Result.success(userGroupImpl.get(name, code, orgRootId));
    }

    @GetMapping("userGroup/properties/")
    @ApiOperation(value = "get user group properties", httpMethod = "GET")
    public Result<List<UserGroupPropertiesDto>> getProperties(@RequestParam Long id) {
        return Result.success(userGroupImpl.getProperties(id));
    }

    @PostMapping("userGroup/properties")
    @ApiOperation(value = "set user group properties", httpMethod = "POST")
    public Result<Boolean> setProperties(@RequestParam Long id, @RequestParam String property, @RequestParam String value, @RequestHeader(name = "userId") Long mender) {
        return Result.success(userGroupImpl.setProperties(id, property, value, mender));
    }

    @GetMapping("userGroup/users/")
    @ApiOperation(value = "get user group users", httpMethod = "GET")
    public Result<List<Long>> getUsers(@RequestParam Long id) {
        return Result.success(userGroupImpl.getUsers(id));
    }

    @PostMapping("userGroup/user")
    @ApiOperation(value = "add user to user group", httpMethod = "POST")
    public Result<Boolean> addUser(@RequestParam Long id, @RequestParam Long userId, @RequestHeader(name = "userId") Long mender) {
        return Result.success(userGroupImpl.addUser(id, userId, mender));
    }

    @DeleteMapping("userGroup/user")
    @ApiOperation(value = "remove user from user group", httpMethod = "DELETE")
    public Result<Boolean> removeUser(@RequestParam Long id, @RequestParam Long userId, @RequestHeader(name = "userId") Long mender) {
        return Result.success(userGroupImpl.removeUser(id, userId, mender));
    }
}
