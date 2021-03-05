package com.soga.generator.model.rules;

import com.soga.generator.model.exception.ClassNotSupportException;
import com.soga.generator.model.exception.RuleParamsException;
import com.soga.generator.model.field.FieldBuilder;
import com.soga.generator.model.field.FieldClass;
import com.soga.generator.model.randoms.RandomDataUtils;
import com.soga.generator.model.utils.DataUtils;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.ToString;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * @description: 成员变量规则
 * @author: lzr
 * @create: 2021-01-10 18:08
 **/
@Getter
@ToString
public class FloatDoubleFieldValueRule<T> extends BasicFieldValueRule implements RuleAction {
    private Class type;
    /***
     * @Description: 最小取值范围()
     * @Author: lzr
     * @Date: 2021/1/10
     */
    private Double minRange;
    /***
     * @Description: 最大取值范围()
     * @Author: lzr
     * @Date: 2021/1/10
     */
    private Double maxRange;

    /***
     * @Description: 精度大小
     * @Author: lzr
     * @Date: 2021/2/25
     * @param null
     * @return:
     */
    private final int scale;

    /***
     * @Description: 可取值(优先级别高于 minRange和maxRange)
     * @Author: lzr
     * @Date: 2021/1/13
     */
    private final List<T> rangeValues;

    FloatDoubleFieldValueRule(Builder<T> builder) {
        setFieldName(builder.fieldName);
        setSubstring(builder.substring);
        minRange = builder.minRange;
        maxRange = builder.maxRange;
        scale = builder.scale;
        rangeValues = builder.rangeValues;
        type = builder.type;
        init();
    }

    @Override
    public Class type() {
        return type;
    }

    @Override
    public void init() {
        if (minRange == null) {
            minRange = type == Float.class ? (double) Float.MIN_VALUE : Double.MIN_VALUE;
        }
        if (maxRange == null) {
            maxRange = type == Float.class ? (double) Float.MAX_VALUE : Double.MAX_VALUE;
        }
    }


    @Override
    public boolean match(Field field) {
        if (isMatch(field)) {
            if (!matchType(field)) {
                throw new ClassNotSupportException(field.getName() + "should be Float or Double");
            }
            if (scale == 0) {
                throw new RuleParamsException("scale can't be zero");
            }
            if (type == Float.class && (minRange < Float.MIN_VALUE || maxRange > Float.MAX_VALUE)) {
                throw new RuleParamsException("minRange or maxRange out of Float range");
            }
            if (type == Double.class && (minRange < Double.MIN_VALUE || maxRange > Double.MAX_VALUE)) {
                throw new RuleParamsException("minRange or maxRange out of Double range");
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean matchType(Field field) {
        Class clazz = field.getType();
        clazz = FieldClass.toClazz(clazz);
        return DataUtils.isAssignableFrom(clazz, Float.class, Double.class);
    }

    @SneakyThrows
    @Override
    public <T> void customAssignFieldValue(Field field, T obj) {
        if (DataUtils.isEmpty(rangeValues)) {
            double value = RandomDataUtils.doubleValue(minRange, maxRange);
            field.set(obj, DataUtils.isAssignableFrom(type, Float.class, float.class) ? (float) value : value);
            return;
        }
        field.set(obj, RandomDataUtils.randomObjFromList(rangeValues));
    }

    @SneakyThrows
    @Override
    public <T> void localizationAssignFieldValue(Field field, T obj) {
        field.set(obj, FieldBuilder.initFieldValue(field));
    }

    @SneakyThrows
    @Override
    public <T> void normalAssignFieldValue(Field field, T obj) {
        field.set(obj, DataUtils.isAssignableFrom(field.getType(), Float.class) ? RandomDataUtils.flagValue() :  RandomDataUtils.doubleValue(null));
    }

    public static abstract class Builder<T> {
        private String fieldName;
        private String substring;
        private Double minRange;
        private Double maxRange;
        private int scale;
        private List<T> rangeValues;
        //对应的class 必须时匿名内部类形式（abstract）
        private Class type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

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

        public Builder minRange(Double minRange) {
            this.minRange = minRange;
            return this;
        }

        public Builder maxRange(Double maxRange) {
            this.maxRange = maxRange;
            return this;
        }

        public Builder scale(int scale) {
            this.scale = scale;
            return this;
        }

        public Builder rangeValues(List<T> rangeValues) {
            this.rangeValues = rangeValues;
            return this;
        }

        public FloatDoubleFieldValueRule build() {
            return new FloatDoubleFieldValueRule(this);
        }

    }
}
