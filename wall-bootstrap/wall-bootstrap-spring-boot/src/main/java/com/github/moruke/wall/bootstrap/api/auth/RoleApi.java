package com.github.moruke.wall.bootstrap.api.auth;

import com.github.moruke.wall.auth.dtos.permission.PermissionDto;
import com.github.moruke.wall.auth.dtos.permission.RoleDto;
import com.github.moruke.wall.auth.service.IRbac;
import com.github.moruke.wall.common.dto.Result;
import com.github.moruke.wall.common.enums.SubjectTypeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/wall/auth")
@Api(tags = "Role")
public class RoleApi {
    @Resource
    private IRbac rbacImpl;

    @PutMapping("role")
    @ApiOperation(value = "Add role", httpMethod = "PUT")
    public Result<Long> add(@RequestBody RoleDto dto) {
        return Result.success(rbacImpl.addRole(dto));
    }

    @DeleteMapping("role/{id}")
    @ApiOperation(value = "Remove role", httpMethod = "DELETE")
    public Result<Boolean> remove(@PathVariable Long id) {
        return Result.success(rbacImpl.removeRole(id));
    }

    @PostMapping("role")
    @ApiOperation(value = "Update role", httpMethod = "POST")
    public Result<Boolean> update(@RequestBody RoleDto dto) {
        return Result.success(rbacImpl.updateRole(dto));
    }

    @GetMapping("role/{id}")
    @ApiOperation(value = "Get role", httpMethod = "GET")
    public Result<RoleDto> get(@PathVariable Long id) {
        return Result.success(rbacImpl.getRole(id));
    }

    @GetMapping("role/{domainId}/{name}")
    @ApiOperation(value = "Get role by name", httpMethod = "GET")
    public Result<RoleDto> get(@PathVariable String name, @PathVariable Long domainId) {
        return Result.success(rbacImpl.getRole(name, domainId));
    }

    @GetMapping("roles")
    @ApiOperation(value = "Get roles", httpMethod = "GET")
    public Result<List<RoleDto>> getRoles(@RequestHeader Long domainId) {
        return Result.success(rbacImpl.getRoles(domainId));
    }

    @PutMapping("role/{roleId}/subject")
    @ApiOperation(value = "Add role for subject", httpMethod = "PUT")
    public Result<Boolean> addRoleForSubject(@PathVariable Long roleId, @RequestHeader Long userId, @RequestParam Long subjectId, @RequestParam SubjectTypeEnum subjectType, @RequestParam(required = false) Date expireTime) {
        return Result.success(rbacImpl.addRoleForSubject(roleId, userId, subjectId, subjectType, expireTime));
    }

    @DeleteMapping("role/{roleId}/subject")
    @ApiOperation(value = "Remove role for subject", httpMethod = "DELETE")
    public Result<Boolean> removeRoleForSubject(@PathVariable Long roleId, @RequestParam Long subjectId, @RequestParam SubjectTypeEnum subjectType) {
        return Result.success(rbacImpl.removeRoleForSubject(roleId, subjectId, subjectType));
    }

    @PutMapping("role/{roleId}/permission")
    @ApiOperation(value = "Add permission for role", httpMethod = "PUT")
    public Result<Boolean> addPermissionForRole(@RequestBody PermissionDto dto) {
        return Result.success(rbacImpl.addPermissionForSubject(dto));
    }

    @DeleteMapping("role/{roleId}/permission")
    @ApiOperation(value = "Remove permission for role", httpMethod = "DELETE")
    public Result<Boolean> removePermissionForRole(@RequestBody PermissionDto dto) {
        return Result.success(rbacImpl.removePermissionForSubject(dto));
    }

    @GetMapping("role/{roleId}/permissions")
    @ApiOperation(value = "Get permissions for role", httpMethod = "GET")
    public Result<List<PermissionDto>> getPermissionsForRole(@PathVariable Long roleId, @RequestHeader Long domainId) {
        return Result.success(rbacImpl.getPermissionsForSubject(roleId, SubjectTypeEnum.ROLE, domainId));
    }

    @GetMapping("role/{roleId}/permissions/{objectId}")
    @ApiOperation(value = "Get object permissions for role", httpMethod = "GET")
    public Result<List<PermissionDto>> getPermissionsForRole(@PathVariable Long roleId, @PathVariable Long objectId, @RequestHeader Long domainId) {
        return Result.success(rbacImpl.getPermissionsForSubject(roleId, SubjectTypeEnum.ROLE, objectId, domainId));
    }

}
