package com.github.moruke.wall.bootstrap.api.authentication;

import com.github.moruke.wall.bootstrap.annotation.Session;
import com.github.moruke.wall.bootstrap.annotation.State;
import com.github.moruke.wall.bootstrap.token.ISessionManager;
import com.github.moruke.wall.common.dto.Result;
import com.github.moruke.wall.identity.authentication.dtos.LoginRequestDto;
import com.github.moruke.wall.identity.authentication.dtos.LoginResponseDto;
import com.github.moruke.wall.identity.authentication.service.IAuthentication;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/wall/aut")
@Api(tags = "Authentication")
public class AuthenticationApi {

    @Resource
    private IAuthentication authenticationImpl;

    @Resource
    private ISessionManager sessionManager;

    @PostMapping("login")
    @ApiOperation(value = "login", httpMethod = "POST")
    @Session
    @State
    public Result<LoginResponseDto> login(@RequestBody LoginRequestDto dto) {
        return Result.success(authenticationImpl.login(dto));
    }

    @GetMapping("logout")
    @ApiOperation(value = "logout", httpMethod = "GET")
    public Result<Boolean> logout(@RequestHeader String sessionId) {
        sessionManager.remove(sessionId);
        return Result.success(true);
    }

    @GetMapping("logoutAllDevice")
    @ApiOperation(value = "logoutAllDevice", httpMethod = "GET")
    // must login
    public Result<Boolean> logoutAllDevice(@RequestHeader String subjectId) {
        sessionManager.removeBySubjectId(subjectId);
        return Result.success(true);
    }

    // TODO 2024/6/20 :forget password
}
