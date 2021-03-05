package com.soga.generator.model.filter;

import com.soga.generator.model.rules.MapFieldValueRule;

/**
 * @description: map过滤器
 * @author: lzr
 * @create: 2021-02-24
 */
public class MapFieldValueFilter extends BasicFieldValueFilter {
    @Override
    Class initClass() {
        return MapFieldValueRule.class;
    }
}
