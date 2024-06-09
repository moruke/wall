package com.github.moruke.wall.common.plugin;

import com.github.moruke.wall.common.enums.SubjectTypeEnum;
import lombok.Data;

@Data
public class BaseRequest implements Request {
    protected Long subjectId;
    protected SubjectTypeEnum subjectType;
    protected String objectName;
    protected String actionName;
    protected Long domainId;
}
