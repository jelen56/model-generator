package com.soga.generator.model.config;

/**
 * @description: 通用配置
 * @author: lzr
 */
public class CommonConfig {

    /*普通模式(生成的值只跟类型有关)*/
    public static int NORMAL_BUILD_MODE=1;

    /*本土化模式(生成的值跟类型有关外还跟fieldName有关,详情见DefaultFieldValueType.java)*/
    public static int LOCALIZATION_BUILD_MODE=2;

    /*自定义模式(生成的值由规则决定,如果匹配不到规则,则降为本土化模式)*/
    public static int CUSTOM_BUILD_MODE=3;

    /*生成模式*/
    public static int BUILD_MODE=CUSTOM_BUILD_MODE;
}
