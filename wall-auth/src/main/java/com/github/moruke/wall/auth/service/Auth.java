package com.github.moruke.wall.auth.service;

import com.github.moruke.wall.common.enums.SubjectTypeEnum;

public interface Auth {
    boolean judge(Long subjectId, SubjectTypeEnum subjectType, Long objectId, Long actionId, String domainId);
}
