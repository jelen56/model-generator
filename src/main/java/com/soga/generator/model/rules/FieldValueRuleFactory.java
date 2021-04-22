package com.soga.generator.model.rules;

import com.soga.generator.model.utils.DataUtils;

import java.util.*;
import java.util.stream.Stream;

/**
 * @description: fieldvalue规则工厂实现类
 * @author: lzr
 * @create: 2021-02-28 02:34
 */
public class FieldValueRuleFactory implements RuleFactory {
    private final Map<Class, List<RuleAction>> actionMap = new HashMap<>();

    public FieldValueRuleFactory() {
        this.init();
    }

    private void init() {
        Arrays.asList(RuleFieldType.values()).forEach(ruleFieldType -> {
            RuleAction[] ruleActions = ruleFieldType.instance();
            Stream.of(ruleActions).forEach(ruleAction -> {
                List<RuleAction> ruleActionList = new ArrayList<>();
                ruleActionList.add(ruleAction);
                actionMap.put(ruleAction.type(), ruleActionList);
            });
        });
    }


    @Override
    public List<RuleAction> getRuleAction(Class ruleClass) {
        List<RuleAction> ruleActions = new ArrayList<>();
        RuleFieldType ruleFieldType = RuleFieldType.getField(ruleClass);
        if (ruleFieldType != null) {
            Class[] classes = ruleFieldType.get();
            for (Class cl : classes) {
                ruleActions.addAll(actionMap.get(cl));
            }
        }
        return ruleActions;
    }

    public static void main(String[] args) {
        List<RuleAction> ruleActions = new ArrayList<>();
        RuleFieldType ruleFieldType = RuleFieldType.getField(WholeNumberFieldValueRule.class);
        Optional.ofNullable(ruleFieldType).ifPresent(ra -> {
            Stream.of(ra.get())
                    .forEach(System.out::println);
        });
    }

    @Override
    public List<RuleAction> findRuleActionByFieldType(Class fieldType) {
        return actionMap.get(fieldType);
    }

    @Override
    public RuleAction getFirstRuleActionByFieldType(Class fieldType) {
        List<RuleAction> ruleActions = actionMap.get(fieldType);
        return DataUtils.isEmpty(ruleActions) ? null : ruleActions.get(0);
    }

    @Override
    public void addRuleAction(RuleAction action) {
        List<RuleAction> actions = actionMap.get(action.type());
        actions.add(action);
        actionMap.put(action.type(), actions);
    }

    @Override
    public boolean hasRule() {
        return !actionMap.isEmpty();
    }


}
