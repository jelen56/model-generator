package com.soga.generator.test.dao.impl;

import com.soga.generator.test.dao.AuthorDao;
import com.soga.generator.test.entity.BlogAuthor;

/**
 * @description: authorDao实现类
 * @author: lzr
 * @create: 2021-03-05 18:13
 */
public class AuthorDaoImpl implements AuthorDao {

    @Override
    public Long save(BlogAuthor blogAuthor) {
        //do saving blogAuthor to DB
        return blogAuthor.getId();
    }
}
