package com.soga.generator.model.filter;

import com.soga.generator.model.rules.WholeNumberFieldValueRule;

/**
 * @description: 整型数过滤器(byte, short, int, long)
 * @author: lzr
 * @create: 2021-02-24
 */
public class WholeNumberFieldValueFilter extends BasicFieldValueFilter {
    @Override
    Class initClass() {
        return WholeNumberFieldValueRule.class;
    }
}
