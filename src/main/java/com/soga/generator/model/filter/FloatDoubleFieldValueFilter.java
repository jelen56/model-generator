package com.soga.generator.model.filter;

import com.soga.generator.model.rules.FloatDoubleFieldValueRule;

/**
 * @description: 浮点数过滤器
 * @author: lzr
 * @create: 2021-02-24 02:52
 */
public class FloatDoubleFieldValueFilter extends BasicFieldValueFilter {
    @Override
    Class initClass() {
        return FloatDoubleFieldValueRule.class;
    }
}
