package com.soga.generator.model.filter;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @description: 成员值过滤器
 * @author: lzr
 * @create: 2021-02-17 23:38
 */
public interface FieldValueFilter {
    /***
     * @Description: 过滤函数
     * @Author: lzr
     * @Date: 2021/2/17
     * @param field
     * @param obj
     * @return: java.util.List<T>
     */
    <T> void doFilter(Field field, T obj, AtomicBoolean isMatch);

}
