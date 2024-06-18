package com.github.moruke.wall.bootstrap.interceptor;

import com.github.moruke.wall.bootstrap.token.ISessionManager;
import com.github.moruke.wall.common.constant.AuthenticationConstant;
import com.github.moruke.wall.common.utils.Precondition;
import com.github.moruke.wall.identity.authentication.service.IToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

public class AuthenticationInterceptor implements HandlerInterceptor {

    @Resource
    private IToken tokenImpl;

    @Resource
    private ISessionManager sessionManager;


    @Value("${wall.rules.authentication.token-prefix}")
    public String TOKEN_PREFIX;
    @Value("#{${wall.rules.authentication.cross-origin}}")
    public Map<String, String> CROSS_ORIGIN;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equalsIgnoreCase(AuthenticationConstant.OPTIONS)) {
            CROSS_ORIGIN.forEach(response::setHeader);
            return true;
        }
        // valid token
        final String token = request.getHeader(TOKEN_PREFIX);
        Precondition.checkState(tokenImpl.validateToken(token), "token is invalid");

        final HttpSession session = request.getSession(false);
        Precondition.checkNotNull(session, "session is null");

        sessionManager.get(session.getId());
        return true;
    }
}
