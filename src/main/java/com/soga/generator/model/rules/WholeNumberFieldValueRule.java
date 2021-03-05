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
public abstract class WholeNumberFieldValueRule<T> extends BasicFieldValueRule implements RuleAction {
    private Class type;
    /***
     * @Description: 最小取值范围()
     * @Author: lzr
     * @Date: 2021/1/10
     */
    private Long minRange;
    /***
     * @Description: 最大取值范围()
     * @Author: lzr
     * @Date: 2021/1/10
     */
    private Long maxRange;

    /***
     * @Description: 可取值(优先级别高于 minRange和maxRange)
     * @Author: lzr
     * @Date: 2021/1/13
     */
    private final List<T> rangeValues;

    WholeNumberFieldValueRule(Builder<T> builder) {
        setFieldName(builder.fieldName);
        setSubstring(builder.substring);
        minRange = builder.minRange;
        maxRange = builder.maxRange;
        rangeValues = builder.rangeValues;
        type=builder.type;
        init();
    }


    @Override
    public void init() {
        if (minRange == null) {
            minRange = getMinRange();
        }
        if (maxRange == null) {
            maxRange = getMaxRange();
        }
    }

    @Override
    public Class type() {
        return type;
    }

    private long getMinRange() {
        long minRangeTemp = 0;
        if (type == Byte.class) {
            minRangeTemp = Byte.MIN_VALUE;
        } else if (type == Short.class) {
            minRangeTemp = Short.MIN_VALUE;
        } else if (type == Long.class) {
            minRangeTemp = Long.MIN_VALUE;
        } else {
            minRangeTemp = Integer.MIN_VALUE;
        }
        return minRangeTemp;
    }

    private long getMaxRange() {
        long maxRangeTemp = 0;
        if (type == Byte.class) {
            maxRangeTemp = Byte.MAX_VALUE;
        } else if (type == Short.class) {
            maxRangeTemp = Short.MAX_VALUE;
        } else if (type == Long.class) {
            maxRangeTemp = Long.MAX_VALUE;
        } else {
            maxRangeTemp = Integer.MAX_VALUE;
        }
        return maxRangeTemp;
    }


    @Override
    public boolean match(Field field) {
        if (isMatch(field)) {
            if (!matchType(field)) {
                throw new ClassNotSupportException(field.getName() + "should be Byte,Short,Integer,Long");
            }
            if (type == Byte.class && (minRange < Byte.MIN_VALUE || maxRange > Byte.MAX_VALUE)) {
                throw new RuleParamsException("minRange or maxRange out of Byte range");
            }
            if (type == Short.class && (minRange < Short.MIN_VALUE || maxRange > Short.MAX_VALUE)) {
                throw new RuleParamsException("minRange or maxRange out of Short range");
            }
            if (type == Integer.class && (minRange < Integer.MIN_VALUE || maxRange > Integer.MAX_VALUE)) {
                throw new RuleParamsException("minRange or maxRange out of Integer range");
            }
            if (type == Long.class && (minRange < Long.MIN_VALUE || maxRange > Long.MAX_VALUE)) {
                throw new RuleParamsException("minRange or maxRange out of Long range");
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean matchType(Field field) {
        Class clazz=field.getType();
        clazz=FieldClass.toClazz(clazz);
        return DataUtils.isAssignableFrom(clazz, Byte.class, Short.class, Integer.class, Long.class);
    }

    @SneakyThrows
    @Override
    public <T> void customAssignFieldValue(Field field, T obj) {
        if (!DataUtils.isEmpty(rangeValues)) {
            field.set(obj, RandomDataUtils.randomObjFromList(rangeValues));
        }else {
            Long random=RandomDataUtils.longValue(minRange, maxRange);
            field.set(obj, random);
        }

    }

    @SneakyThrows
    @Override
    public <T> void localizationAssignFieldValue(Field field, T obj) {
        field.set(obj, FieldBuilder.initFieldValue(field));
    }

    @SneakyThrows
    @Override
    public <T> void normalAssignFieldValue(Field field, T obj) {
        Object value=null;
        if (type == Byte.class) {
            value=RandomDataUtils.byteValue();
        } else if (type == Short.class) {
            value=RandomDataUtils.shortValue(null);
        } else if (type == Integer.class) {
            value=RandomDataUtils.intValue(null);
        } else if (type == Long.class) {
            value=RandomDataUtils.longValue(null);
        }
        field.set(obj, value);
    }

    public static abstract class Builder<T> {
        private String fieldName;
        private String substring;
        private Long minRange;
        private Long maxRange;
        private List<T> rangeValues;
        //对应的class 必须时匿名内部类形式（abstract）
        private Class<T> type=(Class<T>)(((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);

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

        public Builder minRange(Long minRange) {
            this.minRange = minRange;
            return this;
        }

        public Builder maxRange(Long maxRange) {
            this.maxRange = maxRange;
            return this;
        }

        public Builder rangeValues(List<T> rangeValues) {
            this.rangeValues = rangeValues;
            return this;
        }

        public WholeNumberFieldValueRule build() {
            return new WholeNumberFieldValueRule(this){};
        }

    }
}
