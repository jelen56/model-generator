package com.soga.generator.model.filter;

import com.soga.generator.model.rules.ListSetFieldValueRule;

/**
 * @description: list和set过滤器
 * @author: lzr
 * @create: 2021-02-24 03:01
 */
public class ListSetFieldValueFilter extends BasicFieldValueFilter {

    @Override
    Class initClass() {
        return ListSetFieldValueRule.class;
    }
}
