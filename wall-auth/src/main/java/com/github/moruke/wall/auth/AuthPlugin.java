package com.github.moruke.wall.auth;

import com.github.moruke.wall.auth.service.Auth;
import com.github.moruke.wall.auth.service.IAction;
import com.github.moruke.wall.auth.service.IObject;
import com.github.moruke.wall.common.enums.SubjectTypeEnum;
import com.github.moruke.wall.common.plugin.*;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;
@Component
public class AuthPlugin implements Plugin<BaseRequest, VoidResponse> {

    @Resource
    private Auth auth;

    @Resource
    private IAction actionImpl;
    @Resource
    private IObject objectImpl;


    @Override
    public Response execute(Request request) {
        final Judge judge = new Judge(request);
        final boolean result = auth.judge(judge.getSubjectId(), judge.getSubjectType(), judge.getObjectId(), judge.getActionId(), String.valueOf(judge.getDomainId()));
        return new Result(result).toResponse(request);
    }

    @Override
    public void executeNoReturn(Request request) {
        final Judge judge = new Judge(request);
        auth.judge(judge.getSubjectId(), judge.getSubjectType(), judge.getObjectId(), judge.getActionId(), String.valueOf(judge.getDomainId()));
    }

    @Data
    class Judge {
        final Long subjectId;
        final SubjectTypeEnum subjectType;
        final Long objectId;
        final Long actionId;
        final Long domainId;

        Judge(Request request) {
            final BaseRequest baseRequest = (BaseRequest) request;
            this.subjectId = baseRequest.getSubjectId();
            this.subjectType = baseRequest.getSubjectType();
            this.domainId = baseRequest.getDomainId();

            final String objectName = baseRequest.getObjectName();
            final String actionName = baseRequest.getActionName();

            this.objectId = Objects.requireNonNull(objectImpl.get(objectName, domainId), "object not found").getId();
            this.actionId = Objects.requireNonNull(actionImpl.get(actionName, domainId), "action not found").getId();
        }
    }

    @Data
    static class Result {
        boolean result;

        Result(boolean result) {
            this.result = result;
        }

        Response toResponse(Request request) {
            return null;
        }
    }
}
