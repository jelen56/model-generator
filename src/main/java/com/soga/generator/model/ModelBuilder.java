package com.soga.generator.model;

import com.soga.generator.model.config.CommonConfig;
import com.soga.generator.model.exception.ReflectiveException;
import com.soga.generator.model.filter.FieldValueFilterManager;
import com.soga.generator.model.rules.FieldValueRuleManager;
import com.soga.generator.model.rules.StringFieldValueRule;
import com.soga.generator.model.rules.WholeNumberFieldValueRule;
import com.soga.generator.test.entity.vo.BlogArticlesVo;

import java.util.*;


/**
 * @description: model实体构造器
 * @author: lzr
 * @create: 2021-01-07 17:43
 **/
public class ModelBuilder {
    static {
        FieldValueFilterManager.init();
    }

    /***
     * @Description: 随机生成实体模拟数据
     * @Author: lzr
     * @Date: 2021/1/8
     * @param clz
     * @return: T
     */
    public static <T> T generator(Class<? extends T> clz) {
        try {
            return FieldValueRuleManager.generate(clz);
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            throw new ReflectiveException("reflective exception");
        }
    }


    public static void initCommonConfig(int BuildMode) {
        CommonConfig.BUILD_MODE = BuildMode;
    }


    //test
    public static void main(String[] args) {
        init();
        testMutipleThread();
    }

    private static void init() {
        initCommonConfig(CommonConfig.CUSTOM_BUILD_MODE);
        initRules();
    }

    private static void initRules() {
        List<Long> range = new ArrayList<>();
        range.add(111L);
        range.add(222L);
        range.add(333L);
        WholeNumberFieldValueRule<Integer> rule1 = new WholeNumberFieldValueRule.Builder<Long>() {
        }.fieldName("readTimes").minRange((long) 2).maxRange((long) 4).rangeValues(range).build();
        FieldValueRuleManager.addRule(rule1);

        List<String> range2 = new ArrayList<>();
        range2.add("dd");
        range2.add("cc");
        range2.add("aa");
        StringFieldValueRule<String> rule2 = new StringFieldValueRule.Builder<String>() {
        }.fieldName("userName").minLength(1).maxLength(3).rangeValues(range2).build();
        FieldValueRuleManager.addRule(rule2);
    }


    private static void test() {
        BlogArticlesVo blogArticlesVo = null;
        blogArticlesVo = generator(BlogArticlesVo.class);
        System.out.println(blogArticlesVo.toString());
    }

    private static void testMutipleThread() {
        Thread t1 = new Thread(() -> {
            int i = 30;
            while (i > 0) {
                BlogArticlesVo blogArticlesVo = generator(BlogArticlesVo.class);
                System.out.println(blogArticlesVo.toString());
                i--;
            }
        });

        Thread t2 = new Thread(() -> {
            int i = 30;
            while (i > 0) {
                BlogArticlesVo blogArticlesVo = generator(BlogArticlesVo.class);
                System.out.println(blogArticlesVo.toString());
                i--;
            }
        });

        t1.start();
        t2.start();
    }
    //


}
