package com.soga.generator.model.rules;

import com.soga.generator.model.exception.ClassNotSupportException;
import com.soga.generator.model.randoms.DateGenerator;
import com.soga.generator.model.randoms.RandomDataUtils;
import com.soga.generator.model.utils.DataUtils;
import com.soga.generator.model.utils.StringUtils;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.ToString;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

/**
 * @description: 成员变量规则
 * @author: lzr
 * @create: 2021-01-10 18:08
 **/
@Getter
@ToString
public abstract class DateLocalDateFieldValueRule<T> extends BasicFieldValueRule implements RuleAction {
    private Class type;
    /***
     * @Description: 最小取值范围()
     * @Author: lzr
     * @Date: 2021/1/10
     */
    private String minRange;
    /***
     * @Description: 最大取值范围()
     * @Author: lzr
     * @Date: 2021/1/10
     */
    private String maxRange;

    /***
     * @Description: 可取值(优先级别高于 minRange和maxRange)
     * @Author: lzr
     * @Date: 2021/1/13
     */
    private final List<T> rangeValues;

    DateLocalDateFieldValueRule(Builder<T> builder) {
        setFieldName(builder.fieldName);
        setSubstring(builder.substring);
        minRange = builder.minRange;
        maxRange = builder.maxRange;
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
    }

    @Override
    public boolean match(Field field) {
        if (isMatch(field)) {
            if (!matchType(field)) {
                throw new ClassNotSupportException(field.getName() + " should be LocalDateTime,LocalDate,LocalTime or Date");
            }
            if((StringUtils.isEmpty(minRange)|StringUtils.isEmpty(maxRange))|DataUtils.isEmpty(rangeValues)){
                throw new ClassNotSupportException(field.getName() + " minRange and maxRange or rangeValues cannot be empty at the same time");
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean matchType(Field field) {
        return DataUtils.isAssignableFrom(field.getType(), LocalDateTime.class, LocalDate.class, LocalTime.class, Date.class);
    }

    @SneakyThrows
    @Override
    public <T> void customAssignFieldValue(Field field, T obj) {
        if (!DataUtils.isEmpty(rangeValues)) {
            field.set(obj, RandomDataUtils.randomObjFromList(rangeValues));
            return;
        }else if(!StringUtils.isEmpty(minRange)&&!StringUtils.isEmpty(maxRange)){
            if(type==LocalDateTime.class){
                field.set(obj,DateGenerator.randomLocalDateTime(minRange, maxRange));
            }else if(type==LocalDate.class){
                field.set(obj, DateGenerator.randomLocalDate(minRange, maxRange));
            }else if(type==LocalTime.class){
                field.set(obj, DateGenerator.randomLocalTime(minRange, maxRange));
            }else if(type==Date.class){
                field.set(obj, DateGenerator.randomDate(minRange, maxRange));
            }
        }else {
            localizationAssignFieldValue(field,obj);
        }
    }

    @SneakyThrows
    @Override
    public <T> void localizationAssignFieldValue(Field field, T obj) {
        if (type == LocalDateTime.class) {
            field.set(obj, LocalDateTime.now());
        } else if (type == LocalDate.class) {
            field.set(obj, LocalDate.now());
        } else if (type == LocalTime.class) {
            field.set(obj, LocalTime.now());
        } else if (type == Date.class) {
            field.set(obj, new Date());
        }
    }

    @Override
    public <T> void normalAssignFieldValue(Field field, T obj) {
        localizationAssignFieldValue(field,obj);
    }

    public static abstract class Builder<T> {
        private String fieldName;
        private String substring;
        private String minRange;
        private String maxRange;
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

        public Builder minRange(String minRange) {
            this.minRange = minRange;
            return this;
        }

        public Builder maxRange(String maxRange) {
            this.maxRange = maxRange;
            return this;
        }

        public Builder rangeValues(List<T> rangeValues) {
            this.rangeValues = rangeValues;
            return this;
        }

        public DateLocalDateFieldValueRule build() {
            return new DateLocalDateFieldValueRule(this){};
        }

    }
}
