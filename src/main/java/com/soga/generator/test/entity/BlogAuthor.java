package com.soga.generator.test.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

/**
 * @author lzr
 * @date 2021-01-07
 */
@Getter
@Setter
@ToString
public class BlogAuthor extends BaseEntity {
    /**
     * 作者名字
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 头像地址
     */
    private String headPortrait;

    /**
     * 性别
     */
    private Short sex;

    /**
     * 生日
     */
    private LocalDate birth;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 简介
     */
    private String info;

    //test
    private String ipAddress;

    private double latitude;

    private double longitude;

    private String email;


}