package com.soga.generator.model.filter;

import java.lang.reflect.Field;
import java.util.Optional;

/**
 * @description: field值过滤器管理器
 * @author: lzr
 * @create: 2021-02-24 02:32
 */
public class FieldValueFilterManager {
    private static FieldValueFilterChain fieldValueFilterChain=new FieldValueFilterChain();

    public static void init() {
        fieldValueFilterChain.add(new StringFieldValueFilter());
        fieldValueFilterChain.add(new BooleanFieldValueFilter());
        fieldValueFilterChain.add(new FloatDoubleFieldValueFilter());
        fieldValueFilterChain.add(new ListSetFieldValueFilter());
        fieldValueFilterChain.add(new MapFieldValueFilter());
        fieldValueFilterChain.add(new WholeNumberFieldValueFilter());
        fieldValueFilterChain.add(new DateLocalDateFieldValueFilter());
    }


    public static <T> void process(Field field, T obj) {
        fieldValueFilterChain.doFilter(field, obj);
    }
}
