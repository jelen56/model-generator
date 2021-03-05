package com.soga.generator.model.field;

import com.soga.generator.model.config.FieldValueConfig;
import com.soga.generator.model.randoms.RandomDataUtils;
import com.soga.generator.model.rules.FieldValueRuleManager;
import com.soga.generator.model.utils.FieldUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @description: field构建类
 * @author: lzr
 * @create: 2021-03-01 01:44
 */
public class FieldBuilder {
    public static <T> void buildListField(Field field, T obj) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Class clazz = FieldUtils.getListObjClass(field);
        int size = FieldValueConfig.DEFAULT_LIST_COUNT;
        List list = new ArrayList(size);//默认2条数据
        for (int i = 0; i < size; i++) {
            list.add(FieldValueRuleManager.generate(clazz));
        }
        field.set(obj, list);
    }

//    private static <T> void buildVectorField(Field field, T obj) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
//        Class clazz = getListObjClass(field);
//        int size = FieldValueConfig.DEFAULT_LIST_COUNT;
//        Vector vector = new Vector(2);//默认2条数据
//        for (int i = 0; i < size; i++) {
//            vector.add(modelGenerator(clazz));
//        }
//        field.set(obj, vector);
//    }

    public static <T> void buildSetField(Field field, T obj) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Class clazz = FieldUtils.getListObjClass(field);
        int size = FieldValueConfig.DEFAULT_SET_COUNT;
        Set set = new HashSet<>(size);//默认2条数据
        for (int i = 0; i < size; i++) {
            set.add(FieldValueRuleManager.generate(clazz));
        }
        field.set(obj, set);
    }

    public static <T> void buildMapField(Field field, T obj) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Class clazz = FieldUtils.getListObjClass(field);
        int size = FieldValueConfig.DEFAULT_MAP_COUNT;
        Map map = new HashMap<>(size);//默认2条数据
        for (int i = 0; i < size; i++) {
            map.put(RandomDataUtils.stringValue(), FieldValueRuleManager.generate(clazz));
        }
        field.set(obj, map);
    }

    private static FieldValueInitializater defaultFieldValueInitializater = new DefaultFieldValueInitializater();

    public static Object initFieldValue(Field field){
       return defaultFieldValueInitializater.initFieldValue(field);
    }
}
