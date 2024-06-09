package com.github.moruke.wall.auth.annotation;

import com.github.moruke.wall.common.enums.SubjectTypeEnum;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Judge {
    long subjectId() default 0;
    SubjectTypeEnum subjectType() default SubjectTypeEnum.USER;
    String objectName();
    String actionName();
    long domainId();
}
