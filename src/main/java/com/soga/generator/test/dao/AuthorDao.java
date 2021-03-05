package com.soga.generator.test.dao;

import com.soga.generator.test.entity.BlogAuthor;

/**
 * @description: author dao接口
 * @author: lzr
 * @create: 2021-03-05 18:12
 */
public interface AuthorDao {
    Long save(BlogAuthor blogAuthor);
}
