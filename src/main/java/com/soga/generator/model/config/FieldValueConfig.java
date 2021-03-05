package com.soga.generator.model.config;

/**
 * @description: 成员值的配置
 * @author: lzr
 * @create: 2021-02-17 19:39
 */
public class FieldValueConfig {
    /*成员为list类型的默认个数*/
    public final static int DEFAULT_LIST_COUNT = 2;

    /*成员为map类型的默认个数*/
    public final static int DEFAULT_MAP_COUNT = 2;

    /*成员为set类型的默认个数*/
    public final static int DEFAULT_SET_COUNT = 2;

    /*成员为String类型的默认最短长度*/
    public final static int DEFAULT_STRING_MIN_LENGTH = 1;


    /*成员为String类型的默认最长长度*/
    public final static int DEFAULT_STRING_MAX_LENGTH = 100;

    /*成员为Date或者LocalDateTime类型的时区*/
    public final static int TIME_ZONE_OFFSET = +8;

    /*配置当成员属性值是一个链接时,配置host,
     * 比如="https://baidu.com"或者"https://192.168.1.3:8080",
     */
    public final static String BASIC_HOST = null;


    /*经纬度范围设置*/
    public final static double MIN_LAT = 20.1200;
    public final static double MAX_LAT = 25.3100;
    public final static double MIN_LONG = 109.4500;
    public final static double MAX_LONG = 172.000;
    /*经纬度精度大小(小数点后几位)*/
    public final static int LATLONG_SCALE = 4;

}
