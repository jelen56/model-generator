package com.soga.generator.model.rules;

import com.soga.generator.model.config.FieldValueConfig;
import com.soga.generator.model.exception.ClassNotSupportException;
import com.soga.generator.model.exception.RuleParamsException;
import com.soga.generator.model.field.FieldBuilder;
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
public class StringFieldValueRule<T> extends BasicFieldValueRule implements RuleAction {
    private Class type;
    /***
     * @Description: 最短长度
     * @Author: lzr
     * @Date: 2021/1/10
     */
    private Integer minLength;
    /***
     * @Description: 最大长度
     * @Author: lzr
     * @Date: 2021/1/10
     */
    private Integer maxLength;

    /***
     * @Description: 可取值(优先级别高于 minRange和maxRange)
     * @Author: lzr
     * @Date: 2021/1/13
     */
    private final List<T> rangeValues;

    StringFieldValueRule(Builder<T> builder) {
        setFieldName(builder.fieldName);
        setSubstring(builder.substring);
        minLength = builder.minLength;
        maxLength = builder.maxLength;
        rangeValues = builder.rangeValues;
        type=builder.type;
        init();
    }

    @Override
    public Class type() {
        return type;
    }


    @Override
    public void init() {
        if (isChar() && ((minLength!=null&&minLength != 1 )| (maxLength!=null&&maxLength != 1))) {
            throw new RuleParamsException("char type minLength=maxLength=1");
        }
        if (minLength == null) {
            minLength = isChar() ? 1 : FieldValueConfig.DEFAULT_STRING_MIN_LENGTH;
        }
        if (maxLength == null) {
            maxLength = isChar() ? 1 : FieldValueConfig.DEFAULT_STRING_MAX_LENGTH;
        }
    }

    private boolean isChar() {
        return DataUtils.isAssignableFrom(type, Character.class, char.class);
    }


    @Override
    public boolean match(Field field) {
        if (isMatch(field)) {
            if (!matchType(field)) {
                throw new ClassNotSupportException(field.getName() + "should be String");
            }
            if (minLength < FieldValueConfig.DEFAULT_STRING_MIN_LENGTH) {
                throw new RuleParamsException("minLength can't smaller than FieldValueConfig.DEFAULT_STRING_MIN_LENGTH");
            }
            if (maxLength > FieldValueConfig.DEFAULT_STRING_MAX_LENGTH) {
                throw new RuleParamsException("maxLength can't larger than FieldValueConfig.DEFAULT_STRING_MAX_LENGTH");
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean matchType(Field field) {
        return DataUtils.isAssignableFrom(field.getType(), Character.class, String.class);
    }

    @SneakyThrows
    @Override
    public <T> void customAssignFieldValue(Field field, T obj) {
        if (DataUtils.isEmpty(rangeValues)) {
            String value=RandomDataUtils.stringValue(minLength, maxLength);
            field.set(obj, DataUtils.isAssignableFrom(type,Character.class)?value.charAt(0):value);
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
        field.set(obj, DataUtils.isAssignableFrom(field.getType(),Character.class)?RandomDataUtils.charValue():RandomDataUtils.stringValue());
    }

    public abstract static class Builder<T> {
        private String fieldName;
        private String substring;
        private Integer minLength;
        private Integer maxLength;
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

        public Builder minLength(Integer minLength) {
            this.minLength = minLength;
            return this;
        }

        public Builder maxLength(Integer maxLength) {
            this.maxLength = maxLength;
            return this;
        }

        public Builder rangeValues(List<T> rangeValues) {
            this.rangeValues = rangeValues;
            return this;
        }

        public StringFieldValueRule build() {
            return new StringFieldValueRule(this);
        }

    }
}
