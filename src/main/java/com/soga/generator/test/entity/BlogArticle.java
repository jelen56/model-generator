package com.soga.generator.test.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author lzr
 * @date 2021-01-07
 */
@Getter
@Setter
@ToString
public class BlogArticle extends BaseEntity {
    /**
     * 文章名称
     */
    private String title;

    /**
     * html路径
     */
    private String htmlPath;

    /**
     * 预留作者id
     */
    private Long authorId;

    /**
     * 阅读次数
     */
    private Long readTimes;

    /**
     * 发布时间
     */
    private LocalDateTime releaseTime;

    /**
     * 点赞次数
     */
    private Long likeTimes;

    /**
     * 内容
     */
    private String content;
}