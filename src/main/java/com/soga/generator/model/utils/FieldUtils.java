package com.soga.generator.model.utils;

import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * @description: Field工具类
 * @author: lzr
 * @create: 2021-02-25 17:12
 */
public class FieldUtils {

    /***
     * @Description: 获取集合类元素类型(List,Set,Map)
     * @Author: lzr
     * @Date: 2021/2/25
     * @param field
     * @return: java.lang.Class
     */
    public static Class getListObjClass(Field field) throws ClassNotFoundException {
        Type genericType = field.getGenericType();
        ParameterizedTypeImpl parameterizedType = (ParameterizedTypeImpl) genericType; //强转成具体的实现类
        Type[] actualTypes = parameterizedType.getActualTypeArguments();
        return Class.forName(actualTypes[0].getTypeName());
    }
}
