package com.soga.generator.model.rules;

import com.soga.generator.model.config.FieldValueConfig;
import com.soga.generator.model.exception.ClassNotSupportException;
import com.soga.generator.model.field.FieldBuilder;
import com.soga.generator.model.randoms.RandomDataUtils;
import com.soga.generator.model.utils.DataUtils;
import com.soga.generator.model.utils.FieldUtils;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.ToString;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * @description: 成员变量规则
 * @author: lzr
 * @create: 2021-01-10 18:08
 **/
@Getter
@ToString
public class ListSetFieldValueRule<T> extends BasicFieldValueRule implements RuleAction {
    private Class type;
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
    private final List<T> rangeValues;

    ListSetFieldValueRule(Builder<T> builder) {
        setFieldName(builder.fieldName);
        setSubstring(builder.substring);
        size = builder.size;
        rangeValues = builder.rangeValues;
        type=builder.type;
        init();
    }

    @Override
    public void init() {
        if (size == null) {
            size = Math.max(FieldValueConfig.DEFAULT_LIST_COUNT, FieldValueConfig.DEFAULT_SET_COUNT);
        }
    }



    @Override
    public Class type() {
        return type;
    }

    @Override
    public boolean match(Field field) {
        if (isMatch(field)) {
            if (!matchType(field)) {
                throw new ClassNotSupportException(field.getName() + "should be List or Set");
            }
            if (type == List.class) {
                assert size <= Integer.MAX_VALUE - 8 : new IllegalArgumentException("size too large");//ArrayList.MAX_ARRAY_SIZE
            } else if (type == Set.class) {
                assert size <= (1 << 30) : new IllegalArgumentException("size too large");// HashMap.MAXIMUM_CAPACITY
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean matchType(Field field) {
        return DataUtils.isAssignableFrom(field.getType(), List.class, Set.class);
    }

    @SneakyThrows
    @Override
    public <T> void customAssignFieldValue(Field field, T obj) {
        Class clazz = FieldUtils.getListObjClass(field);
        Collection c = null;
        if (DataUtils.isEmpty(rangeValues)) {
            if (List.class.isAssignableFrom(field.getType())) {
                c = new ArrayList(size);
                for (int i = 0; i < size; i++) {
                    c.add(FieldValueRuleManager.generate(clazz));
                }
            } else if (Set.class.isAssignableFrom(field.getType())) {
                c = new HashSet<>(size);
                for (int i = 0; i < size; i++) {
                    c.add(FieldValueRuleManager.generate(clazz));
                }
            }
        } else {
            int rangeSize = rangeValues.size();
            if (List.class.isAssignableFrom(field.getType())) {
                c = new ArrayList(size);
                for (int i = 0; i < size; i++) {
                    c.add(rangeValues.get(RandomDataUtils.intValue(rangeSize)));
                }
            } else if (Set.class.isAssignableFrom(field.getType())) {
                c = new HashSet<>(size);
                for (int i = 0; i < size; i++) {
                    c.add(rangeValues.get(RandomDataUtils.intValue(rangeSize)));
                }
            }
        }
        field.set(obj, c);
    }


    @SneakyThrows
    @Override
    public <T> void localizationAssignFieldValue(Field field, T obj) {
        if (List.class.isAssignableFrom(field.getType())) {
            FieldBuilder.buildListField(field, obj);
        } else if (Set.class.isAssignableFrom(field.getType())) {
            FieldBuilder.buildSetField(field, obj);
        }
    }

    @Override
    public <T> void normalAssignFieldValue(Field field, T obj) {
        localizationAssignFieldValue(field,obj);
    }

    public static abstract class Builder<T> {
        private String fieldName;
        private String substring;
        private Integer size;
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


        public Builder size(Integer size) {
            this.size = size;
            return this;
        }

        public Builder rangeValues(List<T> rangeValues) {
            this.rangeValues = rangeValues;
            return this;
        }

        public ListSetFieldValueRule build() {
            return new ListSetFieldValueRule(this);
        }

    }
}
