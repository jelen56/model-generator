package com.soga.generator.model.rules;

import com.soga.generator.model.config.FieldValueConfig;
import com.soga.generator.model.exception.ClassNotSupportException;
import com.soga.generator.model.exception.RuleParamsException;
import com.soga.generator.model.field.FieldBuilder;
import com.soga.generator.model.randoms.RandomDataUtils;
import com.soga.generator.model.utils.DataUtils;
import com.soga.generator.model.utils.FieldUtils;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.ToString;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 成员变量规则
 * @author: lzr
 * @create: 2021-01-10 18:08
 **/
@Getter
@ToString
public class MapFieldValueRule<T> extends BasicFieldValueRule implements RuleAction {

    /***
     * @Description: 大小
     * @Author: lzr
     * @Date: 2021/2/25
     * @param null
     * @return:
     */
    private Integer size;

    /***
     * @Description: 可取值
     * @Author: lzr
     * @Date: 2021/1/13
     */
    private final Map rangeValues;

    MapFieldValueRule(Builder<T> builder) {
        setFieldName(builder.fieldName);
        setSubstring(builder.substring);
        size = builder.size;
        rangeValues = builder.rangeValues;
        init();
    }


    @Override
    public void init() {
        if (size == null) {
            size = FieldValueConfig.DEFAULT_MAP_COUNT;
        }
    }

    @Override
    public Class type() {
        return Map.class;
    }


    @Override
    public boolean match(Field field) {
        if (isMatch(field)) {
            if (!matchType(field)) {
                throw new ClassNotSupportException(field.getName() + "should be Map");
            }
            assert size <= (1 << 30) : new RuleParamsException("size too large");// HashMap.MAXIMUM_CAPACITY
            return true;
        }
        return false;
    }

    @Override
    public boolean matchType(Field field) {
        return DataUtils.isAssignableFrom(field.getType(), Map.class);
    }

    @SneakyThrows
    @Override
    public <T> void customAssignFieldValue(Field field, T obj) {
        Map map = new HashMap<>(size);//默认2条数据
        Class clazz = FieldUtils.getListObjClass(field);
        if (DataUtils.isEmpty(rangeValues)) {
            for (int i = 0; i < size; i++) {
                map.put(RandomDataUtils.stringValue(), FieldValueRuleManager.generate(clazz));
            }
        } else {
            Object[] keys = rangeValues.keySet().toArray();
            for (int i = 0; i < size; i++) {
                int index = RandomDataUtils.intValue(rangeValues.size());
                Object key = keys[index];
                map.put(keys[index], rangeValues.get(key));
            }
        }
        field.set(obj, map);
    }

    @SneakyThrows
    @Override
    public <T> void localizationAssignFieldValue(Field field, T obj) {
        FieldBuilder.buildMapField(field,obj);
    }

    @Override
    public <T> void normalAssignFieldValue(Field field, T obj) {
        localizationAssignFieldValue(field,obj);
    }

    public static class Builder<T> {
        private String fieldName;
        private String substring;
        private Integer size;
        private Map rangeValues;

        public Builder() {
        }

        public Builder fieldName(String fieldName) {
            this.fieldName = fieldName;
            return this;
        }

        public Builder substring(String substring) {
            this.substring = substring;
            return this;
        }


        public Builder size(Integer size) {
            this.size = size;
            return this;
        }

        public Builder rangeValues(Map rangeValues) {
            this.rangeValues = rangeValues;
            return this;
        }

        public MapFieldValueRule build() {
            return new MapFieldValueRule(this);
        }

    }
}
