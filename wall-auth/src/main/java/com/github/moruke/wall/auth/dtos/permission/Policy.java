package com.github.moruke.wall.auth.dtos.permission;

import com.github.moruke.wall.auth.enums.permission.EffectTypeEnum;
import com.github.moruke.wall.common.enums.SubjectTypeEnum;
import lombok.Data;

import static com.github.moruke.wall.common.constant.CommonConstant.UNDERLINE;

@Data
public class Policy {
    private Long subjectId;
    private SubjectTypeEnum type;
    private Long domainId;
    private Long objectId;
    private Long actionId;
    private EffectTypeEnum effect;
    private int priority;

    public String toPolicy() {
        return type.getId(subjectId) + UNDERLINE + domainId + UNDERLINE + objectId + UNDERLINE + actionId + UNDERLINE + effect.name() + UNDERLINE + priority;
    }

    public static Policy fromPolicy(String policy) {
        String[] policyArr = policy.split(UNDERLINE);
        Policy p = new Policy();

        final String subjectId = policyArr[0] + UNDERLINE + policyArr[1];
        final SubjectTypeEnum type = SubjectTypeEnum.extra(subjectId);
        p.setSubjectId(type.getId(subjectId));
        p.setType(type);
        p.setDomainId(Long.parseLong(policyArr[2]));
        p.setObjectId(Long.parseLong(policyArr[3]));
        p.setActionId(Long.parseLong(policyArr[4]));
        p.setEffect(EffectTypeEnum.find(policyArr[5]));
        p.setPriority(Integer.parseInt(policyArr[6]));
        return p;
    }
}
