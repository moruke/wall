package com.github.moruke.wall.bootstrap.api.account;

import com.github.moruke.wall.account.dto.OrgPropertiesDto;
import com.github.moruke.wall.account.dto.OrganizationDto;
import com.github.moruke.wall.account.service.IOrganization;
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
@Api(tags = "Organization")
public class OrganizationApi {
    @Resource
    private IOrganization orgImpl;

    @PutMapping("org")
    @ApiOperation(value = "add org", httpMethod = "PUT")
    public Result<Long> add(@RequestBody OrganizationDto dto) {
        return Result.success(orgImpl.add(dto));
    }

    @DeleteMapping("org/{id}")
    @ApiOperation(value = "remove org", httpMethod = "DELETE")
    public Result<Boolean> remove(@PathVariable Long id, @RequestHeader(name = "userId") Long mender) {
        return Result.success(orgImpl.remove(id, mender));
    }

    @PostMapping("org/type")
    @ApiOperation(value = "update org type", httpMethod = "POST")
    public Result<Boolean> updateType(@RequestParam Long id, @RequestParam Byte type, @RequestHeader(name = "userId") Long mender) {
        return Result.success(orgImpl.updateType(id, type, mender));
    }

    @PostMapping("org/status")
    @ApiOperation(value = "update org status", httpMethod = "POST")
    public Result<Boolean> updateStatus(@RequestParam Long id, @RequestParam Byte status, @RequestHeader(name = "userId") Long mender) {
        return Result.success(orgImpl.updateStatus(id, status, mender));
    }

    @PostMapping("org/parent")
    @ApiOperation(value = "update org parent", httpMethod = "POST")
    public Result<Boolean> updateParent(@RequestParam Long id, @RequestParam(required = false) Long parentId, @RequestHeader(name = "userId") Long mender) {
        return Result.success(orgImpl.updateParent(id, parentId, mender));
    }

    @PostMapping("org")
    @ApiOperation(value = "update org info", httpMethod = "POST")
    public Result<Boolean> updateInfo(@RequestBody OrganizationDto dto) {
        return Result.success(orgImpl.updateInfo(dto));
    }

    @GetMapping("org/{id}")
    @ApiOperation(value = "get org", httpMethod = "GET")
    public Result<OrganizationDto> get(@PathVariable Long id) {
        return Result.success(orgImpl.get(id));
    }

    @GetMapping("org")
    @ApiOperation(value = "get org", httpMethod = "GET")
    public Result<OrganizationDto> get(@RequestParam(required = false) String name, @RequestParam(required = false) String code, @RequestParam(required = false) Long rootId) {
        return Result.success(orgImpl.get(name, code, rootId));
    }

    @GetMapping("org/children")
    @ApiOperation(value = "get org children", httpMethod = "GET")
    public Result<List<OrganizationDto>> getChildren(@RequestParam Long parentId) {
        return Result.success(orgImpl.getChildren(parentId));
    }

    @GetMapping("org/root")
    @ApiOperation(value = "get org root", httpMethod = "GET")
    public Result<OrganizationDto> getRoot(@RequestParam Long id) {
        return Result.success(orgImpl.getRoot(id));
    }

    @GetMapping("org/parent")
    @ApiOperation(value = "get org parent", httpMethod = "GET")
    public Result<OrganizationDto> getParent(@RequestParam Long id) {
        return Result.success(orgImpl.getParent(id));
    }

    @GetMapping("org/properties")
    @ApiOperation(value = "get org properties", httpMethod = "GET")
    public Result<List<OrgPropertiesDto>> getProperties(@RequestParam Long id) {
        return Result.success(orgImpl.getProperties(id));
    }

    @PostMapping("org/properties")
    @ApiOperation(value = "set org properties", httpMethod = "POST")
    public Result<Boolean> setProperties(@RequestParam Long id, @RequestParam String property, @RequestParam String value, @RequestParam(defaultValue = "false") Boolean overrideChildren, @RequestHeader(name = "userId") Long userId) {
        return Result.success(orgImpl.setProperties(id, property, value, overrideChildren, userId));
    }

    @GetMapping("org/users")
    @ApiOperation(value = "get org users", httpMethod = "GET")
    public Result<List<Long>> getUsers(@RequestParam Long id) {
        return Result.success(orgImpl.getUsers(id));
    }

    @PostMapping("org/users")
    @ApiOperation(value = "add org user", httpMethod = "POST")
    public Result<Boolean> addUser(@RequestParam Long id, @RequestParam Long userId, @RequestHeader(name = "userId") Long mender) {
        return Result.success(orgImpl.addUser(id, userId, mender));
    }

    @DeleteMapping("org/users")
    @ApiOperation(value = "remove org user", httpMethod = "DELETE")
    public Result<Boolean> removeUser(@RequestParam Long id, @RequestParam Long userId, @RequestHeader(name = "userId") Long mender) {
        return Result.success(orgImpl.removeUser(id, userId, mender));
    }

    @GetMapping("org/userGroups")
    @ApiOperation(value = "get org user groups", httpMethod = "GET")
    public Result<List<Long>> getUserGroups(@RequestParam Long id) {
        return Result.success(orgImpl.getUserGroups(id));
    }

    @PostMapping("org/userGroups")
    @ApiOperation(value = "add org user group", httpMethod = "POST")
    public Result<Boolean> addUserGroup(@RequestParam Long id, @RequestParam Long userGroupId, @RequestHeader(name = "userId") Long userId) {
        return Result.success(orgImpl.addUserGroup(id, userGroupId, userId));
    }

    @DeleteMapping("org/userGroups")
    @ApiOperation(value = "remove org user group", httpMethod = "DELETE")
    public Result<Boolean> removeUserGroup(@RequestParam Long id, @RequestParam Long userGroupId, @RequestHeader(name = "userId") Long userId) {
        return Result.success(orgImpl.removeUserGroup(id, userGroupId, userId));
    }
}
