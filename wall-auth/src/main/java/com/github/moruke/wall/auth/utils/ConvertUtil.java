package com.github.moruke.wall.auth.utils;

import com.github.moruke.wall.auth.dao.entity.Object;
import com.github.moruke.wall.auth.dao.entity.*;
import com.github.moruke.wall.auth.dtos.permission.*;
import com.github.moruke.wall.auth.enums.permission.*;
import com.github.moruke.wall.common.dto.DomainDto;
import com.github.moruke.wall.common.enums.SubjectTypeEnum;
import com.github.moruke.wall.common.utils.DateUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Date;
import java.util.List;

import static com.github.moruke.wall.common.constant.CommonConstant.UNDERLINE;
import static java.util.Objects.isNull;

public class ConvertUtil {
    public static ActionDto convert(Action act) {
        ActionDto dto = new ActionDto();
        dto.setId(act.getId());
        dto.setName(act.getName());
        dto.setDescription(act.getDescription());
        dto.setType(ActionTypeEnum.find(act.getType()));
        dto.setStatus(ActionStatusEnum.find(act.getStatus()));
        dto.setDomainDto(new DomainDto(act.getDomainId()));
        dto.setCreator(act.getCreator());
        dto.setMender(act.getMender());
        dto.setCreateTime(act.getCreateTime());
        dto.setModifyTime(act.getModifyTime());
        return dto;
    }

    public static Action convert(ActionDto dto) {
        Action act = new Action();
        act.setId(dto.getId());
        act.setName(dto.getName());
        act.setDescription(dto.getDescription());
        act.setType(dto.getType().getCode());
        act.setStatus(dto.getStatus().getCode());
        act.setDomainId(dto.getDomainDto().getId());
        act.setCreator(dto.getCreator());
        act.setMender(dto.getMender());
        act.setCreateTime(dto.getCreateTime());
        act.setModifyTime(dto.getModifyTime());
        return act;
    }

    public static Object convert(ObjectDto dto) {
        Object obj = new Object();
        obj.setId(dto.getId());
        obj.setName(dto.getName());
        obj.setDescription(dto.getDescription());
        obj.setType(dto.getType().getCode());
        obj.setStatus(dto.getStatus().getCode());
        obj.setDomainId(dto.getDomainDto().getId());
        obj.setCreator(dto.getCreator());
        obj.setMender(dto.getMender());
        obj.setCreateTime(dto.getCreateTime());
        obj.setModifyTime(dto.getModifyTime());
        return obj;
    }

    public static ObjectDto convert(Object obj) {
        ObjectDto dto = new ObjectDto();
        dto.setId(obj.getId());
        dto.setName(obj.getName());
        dto.setDescription(obj.getDescription());
        dto.setType(ObjectTypeEnum.find(obj.getType()));
        dto.setStatus(ObjectStatusEnum.find(obj.getStatus()));
        dto.setDomainDto(new DomainDto(obj.getDomainId()));
        dto.setCreator(obj.getCreator());
        dto.setMender(obj.getMender());
        dto.setCreateTime(obj.getCreateTime());
        dto.setModifyTime(obj.getModifyTime());
        return dto;
    }

    public static Role convert(RoleDto dto) {
        Role role = new Role();
        role.setId(dto.getId());
        role.setName(dto.getName());
        role.setDescription(dto.getDescription());
        role.setType(dto.getType().getCode());
        role.setStatus(dto.getStatus().getCode());
        role.setDomainId(dto.getDomainDto().getId());
        role.setCreator(dto.getCreator());
        role.setMender(dto.getMender());
        role.setCreateTime(dto.getCreateTime());
        role.setModifyTime(dto.getModifyTime());
        return role;
    }

    public static RoleDto convert(Role role) {
        RoleDto dto = new RoleDto();
        dto.setId(role.getId());
        dto.setName(role.getName());
        dto.setDescription(role.getDescription());
        dto.setType(RoleTypeEnum.find(role.getType()));
        dto.setStatus(RoleStatusEnum.find(role.getStatus()));
        dto.setDomainDto(new DomainDto(role.getDomainId()));
        dto.setCreator(role.getCreator());
        dto.setMender(role.getMender());
        dto.setCreateTime(role.getCreateTime());
        dto.setModifyTime(role.getModifyTime());
        return dto;
    }

    public static RoleRelation convert(Long subjectId, SubjectTypeEnum type, Long roleId, Long userId, Date expireTime) {
        RoleRelation roleRelation = new RoleRelation();
        roleRelation.setSubjectId(type.getId(subjectId));
        roleRelation.setRoleId(roleId);
        roleRelation.setExpireTime(isNull(expireTime) ? DateUtils.maxDate() : expireTime);
        roleRelation.setStatus(RoleRelationStatusEnum.DEFAULT.getCode());
        roleRelation.setType(RoleRelationTypeEnum.DEFAULT.getCode());
        roleRelation.setCreator(userId);
        roleRelation.setMender(userId);

        return roleRelation;
    }

    public static PermissionDto convert(Triple<String, String, EffectTypeEnum> triple) {
        PermissionDto dto = new PermissionDto();
        dto.setObjectId(Long.valueOf(triple.getLeft()));
        dto.setActionId(Long.valueOf(triple.getMiddle()));
        dto.setEffect(triple.getRight());
        return dto;
    }

    public static PermissionDto convert(Pair<String, EffectTypeEnum> permission, Long objectId) {
        PermissionDto dto = new PermissionDto();
        dto.setObjectId(objectId);
        dto.setActionId(Long.valueOf(permission.getLeft()));
        dto.setEffect(permission.getRight());
        return dto;
    }

    public static CasbinPolicy convert(List<String> rule, String ptype) {
        CasbinPolicy policy = new CasbinPolicy();
        policy.setPtype(ptype);
        final int size = rule.size();
        if (size > 0) {
            policy.setV0(rule.get(0));
        }
        if (size > 1) {
            policy.setV1(rule.get(1));
        }
        if (size > 2) {
            policy.setV2(rule.get(2));
        }
        if (size > 3) {
            policy.setV3(rule.get(3));
        }
        if (size > 4) {
            policy.setV4(rule.get(4));
        }
        if (size > 5) {
            policy.setV5(rule.get(5));
        }
        return policy;
    }

    public static String convertToString(List<String> rule, String ptype) {
        return ptype + UNDERLINE + String.join(UNDERLINE, rule);
    }

    public static String convertToString(CasbinPolicy casbinPolicy) {
        return casbinPolicy.getPtype() + UNDERLINE + casbinPolicy.getV0() + UNDERLINE + casbinPolicy.getV1() + UNDERLINE + casbinPolicy.getV2() + UNDERLINE + casbinPolicy.getV3() + UNDERLINE + casbinPolicy.getV4() + UNDERLINE + casbinPolicy.getV5();

    }

    public static String convertToLine(CasbinPolicy casbinPolicy) {
        String lineText = casbinPolicy.getPtype();
        if (!"".equals(casbinPolicy.getV0())) {
            lineText += ", " + casbinPolicy.getV0();
        }
        if (!"".equals(casbinPolicy.getV1())) {
            lineText += ", " + casbinPolicy.getV1();
        }
        if (!"".equals(casbinPolicy.getV2())) {
            lineText += ", " + casbinPolicy.getV2();
        }
        if (!"".equals(casbinPolicy.getV3())) {
            lineText += ", " + casbinPolicy.getV3();
        }
        if (!"".equals(casbinPolicy.getV4())) {
            lineText += ", " + casbinPolicy.getV4();
        }
        if (!"".equals(casbinPolicy.getV5())) {
            lineText += ", " + casbinPolicy.getV5();
        }
        return lineText;
    }

    public static Permission convert(PermissionDto dto) {
        Permission permission = new Permission();
        permission.setId(dto.getId());
        permission.setType(dto.getType().getCode());
        permission.setStatus(dto.getStatus().getCode());
        permission.setDomainId(dto.getDomainDto().getId());

        permission.setPolicy(convertToString(dto.getSubjectId(), dto.getSubjectType(), dto.getObjectId(), dto.getActionId(), dto.getDomainDto().getId(), dto.getPriority().getCode(), dto.getEffect().name()));
        permission.setDescription(dto.getDescription());

        permission.setCreator(dto.getCreator());
        permission.setMender(dto.getMender());

        permission.setExpireTime(isNull(dto.getExpireTime()) ? DateUtils.maxDate() : dto.getExpireTime());
        permission.setCreateTime(dto.getCreateTime());
        permission.setModifyTime(dto.getModifyTime());
        return permission;
    }

    public static Permission convert(String description, Long roleId, Long userId, Long subjectId, SubjectTypeEnum subjectType, Long permissionId, Long domainId, Date expireTime) {
        final Permission permission = new Permission();

        permission.setDescription(description);

        permission.setType(PermissionTypeEnum.DEFAULT.getCode());
        permission.setStatus(PermissionStatusEnum.DEFAULT.getCode());
        permission.setDomainId(domainId);

        permission.setPolicy(ConvertUtil.convertToString(subjectId, subjectType, roleId, domainId));
        permission.setExternalId(permissionId);

        permission.setCreator(userId);
        permission.setMender(userId);

        permission.setExpireTime(isNull(expireTime) ? DateUtils.maxDate() : expireTime);

        return permission;
    }

    public static String convertToString(Long subjectId, SubjectTypeEnum type, Long objectId, Long actionId, Long domainId, int priority, String effect) {
        return type.getId(subjectId) + UNDERLINE + domainId + UNDERLINE + objectId + UNDERLINE + actionId + UNDERLINE + effect + UNDERLINE + priority;
    }

    public static String convertToString(Long subjectId, SubjectTypeEnum type, Long roleId, Long domainId) {
        return type.getId(subjectId) + UNDERLINE + roleId + UNDERLINE + domainId;
    }

    public static Policy convert(List<String> policies) {
        Policy p = new Policy();

        final SubjectTypeEnum type = SubjectTypeEnum.extra(policies.get(0));
        p.setType(type);
        p.setSubjectId(type.getId(policies.get(0)));
        p.setDomainId(Long.valueOf(policies.get(1)));
        p.setObjectId(Long.valueOf(policies.get(2)));
        p.setActionId(Long.valueOf(policies.get(3)));
        p.setEffect(EffectTypeEnum.find(policies.get(4)));
        p.setPriority(Integer.parseInt(policies.get(5)));
        return p;
    }

    public static PermissionDto convert(Permission permission) {
        PermissionDto dto = new PermissionDto();
        dto.setId(permission.getId());

        final String p = permission.getPolicy();
        final Policy policy = Policy.fromPolicy(p);

        dto.setId(permission.getId());
        dto.setSubjectId(policy.getSubjectId());
        dto.setSubjectType(policy.getType());
        dto.setObjectId(policy.getObjectId());
        dto.setActionId(policy.getActionId());
        dto.setEffect(policy.getEffect());
        dto.setPriority(PriorityEnum.find(policy.getPriority()));

        dto.setDescription(permission.getDescription());
        dto.setType(PermissionTypeEnum.find(permission.getType()));
        dto.setStatus(PermissionStatusEnum.find(permission.getStatus()));

        dto.setDomainDto(new DomainDto(permission.getDomainId()));


        dto.setCreator(permission.getCreator());
        dto.setMender(permission.getMender());

        dto.setExpireTime(permission.getExpireTime());
        dto.setCreateTime(permission.getCreateTime());
        dto.setModifyTime(permission.getModifyTime());
        return dto;
    }
}
