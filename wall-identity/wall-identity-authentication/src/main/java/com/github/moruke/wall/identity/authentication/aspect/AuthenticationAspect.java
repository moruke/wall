package com.github.moruke.wall.identity.authentication.aspect;

import com.github.moruke.wall.common.utils.Precondition;
import com.github.moruke.wall.identity.authentication.dtos.LoginRequestDto;
import com.github.moruke.wall.identity.authentication.dtos.LoginResponseDto;
import com.github.moruke.wall.identity.authentication.utils.AuthenticationContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthenticationAspect {

    @Pointcut("@annotation(com.github.moruke.wall.identity.authentication.aspect.Authentication)")
    private void AutAspectPointcut() {

    }

    @Around("AutAspectPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        final Object[] args = joinPoint.getArgs();
        Precondition.checkArgument(args.length == 1, "args length must be 1");
        Precondition.checkArgument(args[0] instanceof LoginRequestDto, "args[0] must be LoginRequestDto");

        final LoginRequestDto loginRequestDto = (LoginRequestDto) args[0];

        AuthenticationContext.setRequest(loginRequestDto);

        final Object proceed = joinPoint.proceed();

        AuthenticationContext.setResponse((LoginResponseDto) proceed);

        return proceed;

    }
}