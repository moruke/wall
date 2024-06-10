package com.github.moruke.wall.bootstrap.api.account;

import com.github.moruke.wall.account.dto.UserDto;
import com.github.moruke.wall.account.dto.UserPropertiesDto;
import com.github.moruke.wall.account.service.IUser;
import com.github.moruke.wall.common.dto.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/wall/account")
@Api(tags = "User")
public class UserApi {

    @Resource
    private IUser userImpl;

    @PutMapping("user")
    @ApiOperation(value = "add user", httpMethod = "PUT")
    public Result<Long> add(@RequestBody UserDto dto) {
        return Result.success(userImpl.add(dto));
    }

    @DeleteMapping("user/{id}")
    @ApiOperation(value = "remove user", httpMethod = "DELETE")
    public Result<Boolean> remove(@PathVariable Long id, @RequestHeader(name = "userId") Long mender) {
        return Result.success(userImpl.remove(id, mender));
    }

    @PostMapping("user/type")
    @ApiOperation(value = "update user type", httpMethod = "POST")
    public Result<Boolean> updateType(@RequestParam Long id, @RequestParam Byte type, @RequestHeader(name = "userId") Long mender) {
        return Result.success(userImpl.updateType(id, type, mender));
    }

    @PostMapping("user/status")
    @ApiOperation(value = "update user status", httpMethod = "POST")
    public Result<Boolean> updateStatus(@RequestParam Long id, @RequestParam Byte status, @RequestHeader(name = "userId") Long mender) {
        return Result.success(userImpl.updateStatus(id, status, mender));
    }

    @PostMapping("user")
    @ApiOperation(value = "update user info", httpMethod = "POST")
    public Result<Boolean> updateInfo(@RequestBody UserDto dto) {
        return Result.success(userImpl.updateInfo(dto));
    }

    @GetMapping("user/{id}")
    @ApiOperation(value = "get user", httpMethod = "GET")
    public Result<UserDto> get(@PathVariable Long id) {
        return Result.success(userImpl.get(id));
    }

    @GetMapping("user")
    @ApiOperation(value = "get user by name", httpMethod = "GET")
    public Result<UserDto> get(@RequestParam(required = false) String name, @RequestParam(required = false) String code, @RequestParam Long orgRootId) {
        return Result.success(userImpl.get(name, code, orgRootId));
    }

    @GetMapping("user/properties/{id}")
    @ApiOperation(value = "get user properties", httpMethod = "GET")
    public Result<List<UserPropertiesDto>> getProperties(@PathVariable Long id) {
        return Result.success(userImpl.getProperties(id));
    }

    @PostMapping("user/properties")
    @ApiOperation(value = "set user properties", httpMethod = "POST")
    public Result<Boolean> setProperties(@RequestParam Long id, @RequestParam String property, @RequestParam String value, @RequestHeader(name = "userId") Long mender) {
        return Result.success(userImpl.setProperties(id, property, value, mender));
    }
}
