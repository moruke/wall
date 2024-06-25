package com.github.moruke.wall.bootstrap.aspect;

import com.github.moruke.wall.common.utils.Precondition;
import com.github.moruke.wall.identity.authentication.dtos.LoginRequestDto;
import com.github.moruke.wall.identity.authentication.dtos.LoginResponseDto;
import com.github.moruke.wall.identity.authentication.utils.AuthenticationContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.github.moruke.wall.common.constant.AuthenticationConstant.STATE;

@Aspect
@Component
public class StateAspect {
    @Pointcut("@annotation(com.github.moruke.wall.bootstrap.annotation.State)")
    private void StateAspectPointcut() {

    }

    @Around("StateAspectPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Precondition.checkNotNull(servletRequestAttributes, "ServletRequestAttributes is null");

        final HttpServletRequest request = servletRequestAttributes.getRequest();
        final String state = request.getParameter(STATE);

        final Object proceed = joinPoint.proceed();

        final HttpServletResponse response = servletRequestAttributes.getResponse();
        response.setHeader(STATE, state);

        return proceed;

    }
}
