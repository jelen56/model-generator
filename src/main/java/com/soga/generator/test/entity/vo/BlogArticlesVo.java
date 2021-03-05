package com.soga.generator.test.entity.vo;

import com.soga.generator.test.entity.BlogArticle;
import com.soga.generator.test.entity.BlogAuthor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * @author: lzr
 * @create: 2021-01-08 00:23
 **/
@Setter
@Getter
@ToString
public class BlogArticlesVo {
    /***
     * @Description: 文章列表
     */
    private List<BlogArticle> articles;
    /***
     * @Description: 作者
     */
    private BlogAuthor author;

    //test
    private Date testDate;
}
