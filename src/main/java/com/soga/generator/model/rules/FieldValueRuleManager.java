package com.soga.generator.model.rules;

import com.soga.generator.model.field.FieldClass;
import com.soga.generator.model.filter.*;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @description: FieldValueRule规则管理
 * @author: lzr
 * @create: 2021-02-26 15:17
 */
public class FieldValueRuleManager {
    private static RuleFactory ruleFactory = null;

    static {
        if (ruleFactory == null) {
            ruleFactory = new FieldValueRuleFactory();
        }
    }

    /***
     * @Description: 新增规则
     * @Author: lzr
     * @Date: 2021/2/28
     * @param ruleAction
     * @return: void
     */
    public static void addRule(RuleAction ruleAction) {
        ruleFactory.addRuleAction(ruleAction);
    }

    /***
     * @Description: 获取规则
     * @Author: lzr
     * @Date: 2021/2/28
     * @param ruleClass
     * @return: java.util.List<com.soga.generator.model.rules.RuleAction>
     */
    public static List<RuleAction> getRuleAction(Class ruleClass) {
        return ruleFactory.getRuleAction(ruleClass);
    }

    /***
     * @Description: 根据fieldType获取RuleAction
     * @Author: lzr
     * @Date: 2021/3/3
     * @param fieldType
     * @return: com.soga.generator.model.rules.RuleAction
     */
    public static RuleAction getFirstRuleActionByFieldType(Class fieldType) {
        return ruleFactory.getFirstRuleActionByFieldType(FieldClass.toClazz(fieldType));
    }

    /***
     * @Description: 判断是否配置了规则
     * @Author: lzr
     * @Date: 2021/2/28
    
     * @return: boolean
     */
    public static <T> boolean hasRules() {
        return ruleFactory.hasRule();
    }

    /***
     * @Description: 通过配置指定规则生成相应的实体对象数据
     * @Author: lzr
     * @Date: 2021/2/28
     * @param clz
     * @return: T
     */
    public static <T> T generate(Class<? extends T> clz) throws IllegalAccessException, InstantiationException {
        T obj = clz.newInstance();
        List<Field> fields = new ArrayList<>(Arrays.asList(clz.getDeclaredFields()));
        Class supperClazz = clz.getSuperclass();
        if (supperClazz != null) {
            fields.addAll(Arrays.asList(supperClazz.getDeclaredFields()));
        }
        doGenerotar(fields, obj);
        return obj;
    }

    public static <T> void doGenerotar(List<Field> fields, T obj) {
        for (Field field : fields) {
            if (Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers()) || Modifier.isNative(field.getModifiers())) {
                continue;
            }
            field.setAccessible(true);
            FieldValueFilterManager.process(field, obj);
        }
    }
}

