package com.soga.generator.model.field;

import java.lang.reflect.Field;
import java.util.Optional;

/**
 * @description: field初始化默认实现
 * @author: lzr
 * @create: 2021-01-09 00:27
 **/
public class DefaultFieldValueInitializater extends FieldValueInitializater {
    @Override
    public Object initFieldValue(Field field) {
        Class clazz=field.getType();
        String fieldName=field.getName();
        return Optional.ofNullable(FieldType.getField(FieldClass.toClazz(clazz))).map(fieldType -> fieldType.convert(fieldName)).orElse(null);
    }
}
