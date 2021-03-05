package com.soga.generator.model.filter;

import com.soga.generator.model.rules.StringFieldValueRule;

/**
 * @program: soga-blog
 * @description: 字符串过滤器
 * @author: lzr
 * @create: 2021-02-24
 */
public class StringFieldValueFilter extends BasicFieldValueFilter {
    @Override
    Class initClass() {
        return StringFieldValueRule.class;
    }
}
