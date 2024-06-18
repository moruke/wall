package com.github.moruke.wall.bootstrap.aspect;

import com.github.moruke.wall.bootstrap.annotation.Session;
import com.github.moruke.wall.bootstrap.config.properties.CookieProperties;
import com.github.moruke.wall.bootstrap.config.properties.HeaderProperties;
import com.github.moruke.wall.bootstrap.config.properties.TokenReturnProp;
import com.github.moruke.wall.bootstrap.enums.SessionStatusEnum;
import com.github.moruke.wall.bootstrap.enums.TokenReturnTypeEnum;
import com.github.moruke.wall.bootstrap.token.ISessionManager;
import com.github.moruke.wall.common.utils.Precondition;
import com.github.moruke.wall.identity.authentication.dtos.LoginResponseDto;
import com.github.moruke.wall.identity.authentication.dtos.SessionDto;
import com.github.moruke.wall.identity.authentication.service.IToken;
import com.github.moruke.wall.identity.authentication.utils.AuthenticationContext;
import org.apache.commons.collections4.CollectionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

import static com.github.moruke.wall.common.constant.CommonConstant.EMPTY_STRING;

@Aspect
@Component
public class SessionAspect {

    @Resource
    private ISessionManager sessionManager;

    @Resource
    private TokenReturnTypeEnum type;

    @Resource
    private TokenReturnProp properties;

    @Resource
    private IToken tokenImpl;

    @Pointcut("@annotation(com.github.moruke.wall.bootstrap.annotation.Session)")
    private void SessionAspectPointcut() {

    }

    // if already login , kick other login
    @Before("SessionAspectPointcut()")
    public void before(JoinPoint joinPoint) {
        // check token
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(servletRequestAttributes == null) {
            throw new RuntimeException("ServletRequestAttributes is null");
        }

        final HttpServletRequest request = servletRequestAttributes.getRequest();
        final String token = extraToken(request);
        // check session
        boolean valid = tokenImpl.validateToken(token);
        if (!valid) return; // continue login

        final String sessionId = extraSession(request);
        valid = sessionManager.validate(sessionId);
        if (!valid) return; // continue login

        // kick other login
        // TODO 2024/6/20 : kick all or stop login? maybe ask user is better
        throw new IllegalStateException("already login on other device");
    }

    private String extraSession(HttpServletRequest request) {
        final HttpSession session = request.getSession(false);
        if (Objects.isNull(session)) return EMPTY_STRING;

        return session.getId();
    }

    private String extraToken(HttpServletRequest request) {
        switch (type) {
            case COOKIE:
                return extraCookie(request);
            case HEADER:
                return extraHeader(request);
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    private String extraHeader(HttpServletRequest request) {
        final HeaderProperties headerProperties = (HeaderProperties) properties;
        return request.getHeader(headerProperties.getTokenHeaderName());
    }

    private String extraCookie(HttpServletRequest request) {
        final CookieProperties cookieProperties = (CookieProperties) properties;
        final Cookie[] cookies = request.getCookies();
        if (Objects.isNull(cookies)) return EMPTY_STRING;

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieProperties.getName())) {
                return cookie.getValue();
            }
        }
        return EMPTY_STRING;
    }

    @AfterReturning("SessionAspectPointcut()")
    public void after(JoinPoint joinPoint) {
        final LoginResponseDto loginResponse = AuthenticationContext.getResponse();

        final List<SessionDto> sessions = sessionManager.getBySubjectId(loginResponse.getSubjectId());
        if(CollectionUtils.isNotEmpty(sessions) && sessions.stream().anyMatch(s -> SessionStatusEnum.find(s.getStatus()).valid())){
            throw new IllegalStateException("already login on other device");
        }

        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(servletRequestAttributes == null) {
            throw new RuntimeException("ServletRequestAttributes is null");
        }
        final HttpServletResponse response = servletRequestAttributes.getResponse();

        Precondition.checkNotNull(response, "response is null");

        final String token = loginResponse.getToken();

        switch (type) {
            case COOKIE:
                setCookie(response, token);
                break;
            case HEADER:
                response.addHeader(((HeaderProperties) properties).getTokenHeaderName(), token);
                break;
            case BODY:
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
        final String id = servletRequestAttributes.getRequest().getSession(true).getId();
        if (sessionManager.validate(id)) {
            // already login

            throw new IllegalStateException("already login on other device");
        }
        sessionManager.add(AuthenticationContext.getRequest(), loginResponse, id);
    }

    public void setCookie(HttpServletResponse response, String value) {
        final CookieProperties cookieProperties = (CookieProperties) properties;
        final Cookie cookie = new Cookie(cookieProperties.getName(), value);
        cookie.setMaxAge(cookieProperties.getMaxAge());
        cookie.setHttpOnly(cookieProperties.isHttpOnly());
        cookie.setPath(cookieProperties.getPath());
        cookie.setDomain(cookieProperties.getDomain());
        cookie.setSecure(cookieProperties.isSecure());
        response.addCookie(cookie);
    }
}
