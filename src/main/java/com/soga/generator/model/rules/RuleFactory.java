package com.soga.generator.model.rules;

import java.util.List;

/**
 * @description: fieldvalue规则工厂
 * @author: lzr
 * @create: 2021-02-27
 */
public interface RuleFactory {
    /***
     * @Description: 根据类选择对应规则行为
     * @Author: lzr
     * @Date: 2021/2/28
     * @param ruleClass
     * @return: com.soga.common.model.rules.RuleAction
     */
    List<RuleAction> getRuleAction(Class ruleClass);

    /***
     * @Description: 根据类选择对应规则行为
     * @Author: lzr
     * @Date: 2021/2/28
     * @param fieldType
     * @return: com.soga.common.model.rules.RuleAction
     */
    List<RuleAction> findRuleActionByFieldType(Class fieldType);

    /***
     * @Description: 根据类选择对应规则行为
     * @Author: lzr
     * @Date: 2021/2/28
     * @param fieldType
     * @return: com.soga.common.model.rules.RuleAction
     */
    RuleAction getFirstRuleActionByFieldType(Class fieldType);
    
    /***
     * @Description: 新增规则行为
     * @Author: lzr
     * @Date: 2021/2/28
     * @param action
     * @return: void
     */
    void addRuleAction(RuleAction action);
    
    /***
     * @Description: 判断是否有规则
     * @Author: lzr
     * @Date: 2021/2/28
    
     * @return: boolean
     */
    boolean hasRule();
}
