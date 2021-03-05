package com.soga.generator.model;

import com.soga.generator.test.dao.AuthorDao;
import com.soga.generator.test.dao.impl.AuthorDaoImpl;
import com.soga.generator.test.entity.BlogAuthor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;


/**
 * @description: 实体类生成测试
 * @author: lzr
 * @create: 2021-03-05 18:04
 */
public class GeneratorTest {
    private AuthorDao authorDao;

    @Before
    public void init() {
        authorDao = Mockito.spy(AuthorDaoImpl.class);
    }

    @Test
    public void testSave() {
        BlogAuthor blogAuthor = ModelBuilder.generator(BlogAuthor.class);
//        Mockito.when(authorDao.save(blogAuthor)).thenReturn(blogAuthor.getId());
        Assert.assertEquals(blogAuthor.getId(), authorDao.save(blogAuthor).longValue());
        Mockito.verify(authorDao, Mockito.times(1)).save(blogAuthor);
    }

}
