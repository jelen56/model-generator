package com.soga.generator.model.utils;


/**
 * @description: 字符串工具类
 * @author: lzr
 * @create: 2021-02-28 00:41
 */
public class StringUtils {
    public static boolean isEmpty(String target) {
        return target == null || target.equals("");
    }

    private static String upperCase(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }
}
