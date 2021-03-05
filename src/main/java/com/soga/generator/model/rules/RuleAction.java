package com.soga.generator.model.rules;

import java.lang.reflect.Field;

/**
 * @description: 规则定义（类似BeanDefinition）
 * @author: lzr
 * @create: 2021-02-27 19:31
 */
public interface RuleAction {
    /***
     * @Description: 获取规则的类型
     * @Author: lzr
     * @Date: 2021/2/28

     * @return: java.lang.Class
     */
    Class type();


    /***
     * @Description: 初始化
     * @Author: lzr
     * @Date: 2021/2/28

     * @return: void
     */
    void init();

//    /***
//     * @Description: 检查初始化时的参数
//     * @Author: lzr
//     * @Date: 2021/2/28
//
//     * @return: void
//     */
//    void afterInit() throws RuleParamsException;

    /***
     * @Description: 规则(类型和规则)是否匹配
     * @Author: lzr
     * @Date: 2021/2/28
     * @param field
     * @return: void
     */
    boolean match(Field field);

    /***
     * @Description: 类型是否匹配
     * @Author: lzr
     * @Date: 2021/3/1
     * @param field
     * @return: boolean
     */
    boolean matchType(Field field);
    
    /***
     * @Description: 根据规则给field赋值（自定义化模式）
     * @Author: lzr
     * @Date: 2021/2/28
     * @param field
 * @param obj
     * @return: void
     */
    <T> void customAssignFieldValue(Field field, T obj);

    /***
     * @Description:根据field.type 默认赋值(本土化模式)
     * @Author: lzr
     * @Date: 2021/3/1
     * @param field
     * @param obj
     * @return: void
     */
    <T> void localizationAssignFieldValue(Field field, T obj);

    /***
     * @Description:根据field.type 赋值(普通模式)
     * @Author: lzr
     * @Date: 2021/3/1
     * @param field
     * @param obj
     * @return: void
     */
    <T> void normalAssignFieldValue(Field field, T obj);

}

