package com.soga.generator.model.filter;

import com.soga.generator.model.config.CommonConfig;
import com.soga.generator.model.exception.ReflectiveException;
import com.soga.generator.model.rules.FieldValueRuleManager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @description: 过滤链实现
 * @author: lzr
 * @create: 2021-02-24 00:36
 */
public class FieldValueFilterChain {
    private static List<FieldValueFilter> fieldValueFilters = new ArrayList();


    public void add(FieldValueFilter fieldValueFilter) {
        this.fieldValueFilters.add(fieldValueFilter);
    }

    /***
     * @Description: 分为三个步骤:1.先匹配自定义的规则；2.匹配默认规则；3.其他类型递归往下执行
     * @Author: lzr
     * @Date: 2021/3/4
     * @param field
     * @param obj
     * @return: void
     */
    public <T> void doFilter(Field field, T obj) {
        if (CommonConfig.BUILD_MODE == CommonConfig.LOCALIZATION_BUILD_MODE) {
            doLocalizationAssign(field, obj, null);
        } else if (CommonConfig.BUILD_MODE == CommonConfig.CUSTOM_BUILD_MODE) {
            doCustomAssign(field, obj);
        } else {
            doNormalAssignFieldValue(field, obj);
        }
    }

    /***
     * @Description: 自定义模式
     * @Author: lzr
     * @Date: 2021/3/4
     * @param field
     * @param obj
     * @return: void
     */
    private <T> void doCustomAssign(Field field, T obj) {
        AtomicBoolean isMatch = new AtomicBoolean(false);
        //1.
        fieldValueFilters.forEach(fieldValueFilter -> fieldValueFilter.doFilter(field, obj, isMatch));
        //2.规则匹配不到则根据fieldType获取相应默认的规则去执行生成相关的数据
        if (!isMatch.get()) {
            doLocalizationAssign(field, obj, () -> {
                isMatch.set(true);
            });

        }
        if (!isMatch.get()) {
            //3.除了规则相关的类型外的其他类型在这里处理
            noMatch(field, obj);
        }
    }

    /***
     * @Description: 本地化模式
     * @Author: lzr
     * @Date: 2021/3/4
     * @param field
     * @param obj
     * @return: void
     */
    private <T> void doLocalizationAssign(Field field, T obj, Runnable runnable) {
        AtomicBoolean isMatch = new AtomicBoolean(false);
        Optional.ofNullable(FieldValueRuleManager.getFirstRuleActionByFieldType(field.getType()))
                .filter(action ->
                        action.matchType(field))
                .ifPresent(action -> {
                    action.localizationAssignFieldValue(field, obj);
                    isMatch.set(true);
                    Optional.ofNullable(runnable).ifPresent(r -> r.run());
                });
        if (!isMatch.get()) {
            noMatch(field, obj);
        }
    }

    /***
     * @Description: 普通模式
     * @Author: lzr
     * @Date: 2021/3/4
     * @param field
     * @param obj
     * @return: void
     */
    private <T> void doNormalAssignFieldValue(Field field, T obj) {
        AtomicBoolean isMatch = new AtomicBoolean(false);
        Optional.ofNullable(FieldValueRuleManager.getFirstRuleActionByFieldType(field.getType()))
                .filter(action ->
                        action.matchType(field))
                .ifPresent(action -> {
                    action.normalAssignFieldValue(field, obj);
                    isMatch.set(true);
                });
        if (!isMatch.get()) {
            noMatch(field, obj);
        }
    }

    private <T> void noMatch(Field field, T obj) {
        try {
            field.set(obj, FieldValueRuleManager.generate(field.getType()));
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            throw new ReflectiveException(e.getMessage());
        }
    }
}