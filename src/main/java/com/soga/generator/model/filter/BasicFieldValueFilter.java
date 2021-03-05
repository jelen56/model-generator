package com.soga.generator.model.filter;

import com.soga.generator.model.rules.FieldValueRuleManager;
import com.soga.generator.model.rules.RuleAction;

import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @description: 基础过滤器
 * @author: lzr
 * @create: 2021-03-01 21:45
 */
public abstract class BasicFieldValueFilter implements FieldValueFilter {
    @Override
    public <T> void doFilter(Field field, T obj, AtomicBoolean isMatch) {
        List<RuleAction> ruleActions = FieldValueRuleManager.getRuleAction(initClass());
        ruleActions.stream()
                .filter(ruleAction -> ruleAction.match(field))
                .findFirst()
                .ifPresent(ruleAction -> {
                    ruleAction.customAssignFieldValue(field, obj);
                    isMatch.set(true);
                });
    }

    abstract Class initClass();

}
