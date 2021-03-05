package com.soga.generator.model.utils;

import java.util.*;

/**
 * @description: 数据工具类
 * @author: lzr
 * @create: 2021-02-28 18:10
 */
public class DataUtils {
    public static boolean isEmpty(Collection collection) {
        return collection == null ? true : collection.isEmpty();
    }

    public static boolean isEmpty(Map map) {
        return map == null ? true : map.isEmpty();
    }

    public static boolean isAssignableFrom(Class clazz, Class... classes) {
        for (Class cl : classes) {
            if (clazz == cl | clazz.isAssignableFrom(cl)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        test();
    }

    private static void test(){
        Map<String, List> map=new HashMap<>();
        System.out.println(Optional.ofNullable(map.get("d")).get().get(0));
    }
}
