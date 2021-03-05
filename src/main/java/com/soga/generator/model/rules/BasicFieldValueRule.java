package com.soga.generator.model.rules;

import com.soga.generator.model.utils.StringUtils;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;

/**
 * @description: 基本规则
 * @author: lzr
 * @create: 2021-02-24
 */
@Setter
@Getter
public class BasicFieldValueRule<T> {
    /*属性名字,用于单个精确匹配*/
    private String fieldName;
    /*包含该字字符串,用于批量匹配*/
    private String substring;


    /***
     * @Description: field 赋值时匹配是否符合规则
     * @Author: lzr
     * @Date: 2021/2/28
     * @param field
     * @return: boolean
     */
    public boolean isMatch(Field field) {
        return (!StringUtils.isEmpty(fieldName) && fieldName.equals(field.getName())) || (!StringUtils.isEmpty(substring) && field.getName().contains(substring));
    }

}
