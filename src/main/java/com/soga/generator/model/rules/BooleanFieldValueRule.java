package com.soga.generator.model.rules;

import com.soga.generator.model.exception.ClassNotSupportException;
import com.soga.generator.model.exception.RuleParamsException;
import com.soga.generator.model.field.FieldBuilder;
import com.soga.generator.model.field.FieldClass;
import com.soga.generator.model.randoms.RandomDataUtils;
import com.soga.generator.model.utils.DataUtils;
import lombok.*;

import java.lang.reflect.Field;

/**
 * @description: 成员变量规则
 * @author: lzr
 * @create: 2021-01-10 18:08
 **/
@Setter
@Getter
@ToString
@AllArgsConstructor
public class BooleanFieldValueRule extends BasicFieldValueRule implements RuleAction {
    /*默认值*/
    private final Boolean value;

    BooleanFieldValueRule(BooleanFieldValueRule.Builder builder) {
        setFieldName(builder.fieldName);
        setSubstring(builder.substring);
        value = builder.value;
        init();
    }

    @Override
    public Class type() {
        return Boolean.class;
    }

    @Override
    public void init() {
    }

    @Override
    public boolean match(Field field) {
        if(isMatch(field)){
            if(!matchType(field)){
                throw new ClassNotSupportException(field.getName() + "should be Boolean");
            }
            if (value == null) {
                throw new RuleParamsException("value can't be null");
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean matchType(Field field) {
        Class clazz=field.getType();
        clazz= FieldClass.toClazz(clazz);
        return DataUtils.isAssignableFrom(clazz, Boolean.class);
    }

    @SneakyThrows
    @Override
    public <T> void customAssignFieldValue(Field field, T obj) {
        field.set(obj, value);
    }

    @SneakyThrows
    @Override
    public <T> void localizationAssignFieldValue(Field field, T obj) {
        field.set(obj, FieldBuilder.initFieldValue(field));
    }

    @SneakyThrows
    @Override
    public <T> void normalAssignFieldValue(Field field, T obj) {
        field.set(obj, RandomDataUtils.booleanValue());
    }

    public static class Builder {
        private String fieldName;
        private String substring;
        private Boolean value;

        public Builder() {
        }

        public BooleanFieldValueRule.Builder fieldName(String fieldName) {
            this.fieldName = fieldName;
            return this;
        }

        public BooleanFieldValueRule.Builder substring(String substring) {
            this.substring = substring;
            return this;
        }

        public BooleanFieldValueRule.Builder value(Boolean value) {
            this.value = value;
            return this;
        }

        public BooleanFieldValueRule build() {
            return new BooleanFieldValueRule(this);
        }

    }
}
