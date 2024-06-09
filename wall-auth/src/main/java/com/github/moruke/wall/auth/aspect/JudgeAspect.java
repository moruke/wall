package com.github.moruke.wall.auth.aspect;

import com.github.moruke.wall.auth.service.Auth;
import com.github.moruke.wall.auth.service.IAction;
import com.github.moruke.wall.auth.service.IObject;
import com.github.moruke.wall.common.utils.Context;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Objects;

@Aspect
@Component
@Slf4j
class JudgeAspect {

    @Resource
    private Auth auth;

    @Resource
    private IObject objectImpl;

    @Resource
    private IAction actionImpl;

    @Pointcut("@annotation(com.github.moruke.wall.auth.annotation.Judge)")
    private void JudgeAspectPointcut() {

    }

    @Before("JudgeAspectPointcut()")
    public void judge(JoinPoint joinPoint) {

        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        final com.github.moruke.wall.auth.annotation.Judge annotation = method.getAnnotation(com.github.moruke.wall.auth.annotation.Judge.class);

        long subjectId = annotation.subjectId();
        if (subjectId <= 0) {
            // get subjectId from context
            subjectId = Context.getSubjectId();
        }

        long domainId = annotation.domainId();
        if (domainId <= 0) {
            // get domainId from context
            domainId = Context.getDomain().getId();
        }

        final Long actionId = Objects.requireNonNull(actionImpl.get(annotation.actionName(), domainId), "action not found").getId();
        final Long objectId = Objects.requireNonNull(objectImpl.get(annotation.objectName(), domainId), "object not found").getId();

        final boolean judge = auth.judge(subjectId, annotation.subjectType(), objectId, actionId, String.valueOf(domainId));
        if (!judge) {
            throw new RuntimeException("permission denied");
        }
    }

}
