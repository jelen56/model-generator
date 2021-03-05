package com.soga.generator.model;

import com.soga.generator.model.config.CommonConfig;
import com.soga.generator.test.dao.AuthorDao;
import com.soga.generator.test.dao.impl.AuthorDaoImpl;
import com.soga.generator.test.entity.BlogAuthor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * @description: 本土化构建模式
 * @author: lzr
 * @create: 2021-03-05 23:53
 */
public class NormalBuildModeTest {
    private AuthorDao authorDao;

    @Before
    public void init() {
        authorDao = Mockito.spy(AuthorDaoImpl.class);
        initConfig();
    }

    private void initConfig() {
        ModelBuilder.initCommonConfig(CommonConfig.NORMAL_BUILD_MODE);
    }

    @Test
    public void testSave() {
        BlogAuthor blogAuthor = ModelBuilder.generator(BlogAuthor.class);
        System.out.println(blogAuthor);
        Assert.assertEquals(blogAuthor.getId(), authorDao.save(blogAuthor).longValue());
        Mockito.verify(authorDao, Mockito.times(1)).save(blogAuthor);
    }
}
