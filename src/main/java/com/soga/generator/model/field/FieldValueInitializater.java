package com.soga.generator.model.field;

import java.lang.reflect.Field;

/**
 * @description: field初始化抽象类
 * @author: lzr
 * @create: 2021-01-09 00:24
 **/
public abstract class FieldValueInitializater {
    public abstract Object initFieldValue(Field field);
}

