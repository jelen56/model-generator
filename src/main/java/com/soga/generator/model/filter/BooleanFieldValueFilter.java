package com.soga.generator.model.filter;

import com.soga.generator.model.rules.BooleanFieldValueRule;

/**
 * @description: 布尔类型过滤器
 * @author: lzr
 * @create: 2021-02-24 03:00
 */
public class BooleanFieldValueFilter extends BasicFieldValueFilter {
    @Override
    Class initClass() {
        return BooleanFieldValueRule.class;
    }
}
