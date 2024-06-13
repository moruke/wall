package com.github.moruke.wall.auth.core.impl.casbin;

import com.github.moruke.wall.auth.configuration.CasbinProperties;
import com.github.moruke.wall.auth.dao.entity.CasbinPolicy;
import com.github.moruke.wall.auth.dao.mapper.CasbinPolicyMapper;
import com.github.moruke.wall.auth.dao.mapper.PermissionMapper;
import com.github.moruke.wall.auth.utils.ConvertUtil;
import com.github.moruke.wall.common.utils.Context;
import com.github.moruke.wall.common.utils.Precondition;
import org.apache.commons.collections4.CollectionUtils;
import org.casbin.jcasbin.exception.CasbinAdapterException;
import org.casbin.jcasbin.model.Assertion;
import org.casbin.jcasbin.model.Model;
import org.casbin.jcasbin.persist.*;

import java.util.*;
import java.util.stream.Collectors;

public class MybatisAdapter implements Adapter, BatchAdapter, UpdatableAdapter, FilteredAdapter {

    private final CasbinPolicyMapper casbinPolicyMapper;
    private final PermissionMapper permissionMapper;
    private final CasbinProperties casbinProperties;

    private boolean isFiltered = false;

    public MybatisAdapter(CasbinPolicyMapper casbinPolicyMapper, PermissionMapper permissionMapper, CasbinProperties casbinProperties) {
        this.casbinPolicyMapper = casbinPolicyMapper;
        this.permissionMapper = permissionMapper;
        this.casbinProperties = casbinProperties;
    }

    @Override
    public void addPolicies(String sec, String ptype, List<List<String>> rules) {
        if (CollectionUtils.isEmpty(rules)) {
            return;
        }

        final List<CasbinPolicy> policies = new ArrayList<>(rules.size());

        for (List<String> rule : rules) {
            CasbinPolicy line = ConvertUtil.convert(rule, ptype);
            policies.add(line);
        }

        casbinPolicyMapper.batchInsert(policies);
        Context.setPermissionIds(policies.stream().map(CasbinPolicy::getId).collect(Collectors.toList()));
    }

    @Override
    public void removePolicies(String sec, String ptype, List<List<String>> rules) {
        if (CollectionUtils.isEmpty(rules)) {
            return;
        }

        final List<Long> permissionIds = Context.getPermissionIds();

        Precondition.checkNotEmpty(permissionIds, "permissionIds is empty");

        casbinPolicyMapper.batchDelete(permissionIds);
    }

    @Override
    public void loadFilteredPolicy(Model model, Object filter) throws CasbinAdapterException {
        if (filter == null) {
            loadPolicy(model);
            isFiltered = false;
            return;
        }
        if (!(filter instanceof org.casbin.jcasbin.persist.file_adapter.FilteredAdapter.Filter)) {
            isFiltered = false;
            throw new CasbinAdapterException("Invalid filter type.");
        }
        loadFilteredPolicyFile(model, (org.casbin.jcasbin.persist.file_adapter.FilteredAdapter.Filter) filter, Helper::loadPolicyLine);
        isFiltered = true;
    }

    @Override
    public boolean isFiltered() {
        return isFiltered;
    }

    @Override
    public void updatePolicy(String sec, String ptype, List<String> oldRule, List<String> newRule) {
        if (CollectionUtils.isEmpty(oldRule) || CollectionUtils.isEmpty(newRule)) {
            return;
        }

        final List<Long> permissionIds = Context.getPermissionIds();
        Precondition.checkNotNull(permissionIds, "permissionIds is null");
        final Long id = permissionIds.get(0);

        casbinPolicyMapper.deleteByPrimaryKey(id);
        casbinPolicyMapper.insert(ConvertUtil.convert(newRule, ptype));
    }

    @Override
    public void loadPolicy(Model model) {
        final List<CasbinPolicy> policies = casbinPolicyMapper.selectAllPolicy();
        if (CollectionUtils.isEmpty(policies)) {
            return;
        }

        policies.stream().map(ConvertUtil::convertToLine).forEach(line -> Helper.loadPolicyLine(line, model));
    }

    @Override
    public void savePolicy(Model model) {
        // clear all policy
        casbinPolicyMapper.deleteAll();

        final List<CasbinPolicy> policies = saveSectionPolicyWithBatch(model, "p");
        if (CollectionUtils.isNotEmpty(policies)) {
            casbinPolicyMapper.batchInsert(policies);
        }

        policies.clear();
        final List<CasbinPolicy> gPolicies = saveSectionPolicyWithBatch(model, "g");
        if (CollectionUtils.isNotEmpty(gPolicies)) {
            casbinPolicyMapper.batchInsert(gPolicies);
        }
    }

    @Override
    public void addPolicy(String sec, String ptype, List<String> rule) {
        if (CollectionUtils.isEmpty(rule)) {
            return;
        }
        List<List<String>> rules = new ArrayList<>();
        rules.add(rule);
        this.addPolicies(sec, ptype, rules);
    }

    @Override
    public void removePolicy(String sec, String ptype, List<String> rule) {
        if (CollectionUtils.isEmpty(rule)) {
            return;
        }
        List<List<String>> rules = new ArrayList<>();
        rules.add(rule);
        this.removePolicies(sec, ptype, rules);
    }

    @Override
    public void removeFilteredPolicy(String sec, String ptype, int fieldIndex, String... fieldValues) {
        final List<String> line = new ArrayList<>();

        for (int i = 0; i < fieldIndex; i++) {
            line.add("");
        }

        line.addAll(Arrays.asList(fieldValues));

        final List<Long> permissionIds = Context.getPermissionIds();
        if (CollectionUtils.isEmpty(permissionIds)) {
            return;
        }
        casbinPolicyMapper.batchDelete(permissionIds);
    }


    private void loadFilteredPolicyFile(Model model, org.casbin.jcasbin.persist.file_adapter.FilteredAdapter.Filter filter, Helper.loadPolicyLineHandler<String, Model> handler) {
        throw new UnsupportedOperationException("not implemented");
    }

    private List<CasbinPolicy> saveSectionPolicyWithBatch(Model model, String section) {
        if (!model.model.containsKey(section)) return Collections.emptyList();
        final List<CasbinPolicy> policies = new ArrayList<>();
        for (Map.Entry<String, Assertion> entry : model.model.get(section).entrySet()) {
            String ptype = entry.getKey();
            Assertion ast = entry.getValue();

            for (List<String> rule : ast.policy) {
                CasbinPolicy policy = ConvertUtil.convert(rule, ptype);
                policies.add(policy);
            }
        }
        return policies;
    }
}
