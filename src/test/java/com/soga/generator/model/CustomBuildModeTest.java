package com.soga.generator.model;

import com.soga.generator.model.config.CommonConfig;
import com.soga.generator.model.rules.FieldValueRuleManager;
import com.soga.generator.model.rules.StringFieldValueRule;
import com.soga.generator.model.rules.WholeNumberFieldValueRule;
import com.soga.generator.test.dao.AuthorDao;
import com.soga.generator.test.dao.impl.AuthorDaoImpl;
import com.soga.generator.test.entity.BlogAuthor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;


/**
 * @description: 实体类生成测试
 * @author: lzr
 * @create: 2021-03-05 18:04
 */
public class CustomBuildModeTest {
    private AuthorDao authorDao;

    @Before
    public void init() {
        authorDao = Mockito.spy(AuthorDaoImpl.class);
        initConfig();
        initRules();
    }

    /***
     * @Description: 初始化构建模式
     * @Author: lzr
     * @Date: 2021/3/6
     * @return: void
     */
    private void initConfig() {
        ModelBuilder.initCommonConfig(CommonConfig.CUSTOM_BUILD_MODE);
    }

    /***
     * @Description: 初始化自定义规则
     * @Author: lzr
     * @Date: 2021/3/6
     * @return: void
     */
    private void initRules() {
        StringFieldValueRule<String> rule1=new StringFieldValueRule.Builder<String>(){}.fieldName("password").minLength(4).maxLength(5).build();
        List<String> range = new ArrayList<>();
        range.add("dd");
        range.add("cc");
        range.add("aa");
        StringFieldValueRule<String> rule2 = new StringFieldValueRule.Builder<String>() {//其中Builder<T> 中的T类型必须跟range集合中元素的类型匹配
        }.fieldName("userName").minLength(1).maxLength(3).rangeValues(range).build();
        FieldValueRuleManager.addRule(rule1);
        FieldValueRuleManager.addRule(rule2);
    }

    /***
     * @Description: 测试例子
     * @Author: lzr
     * @Date: 2021/3/6
     * @return: void
     */
    @Test
    public void testSave() {
        BlogAuthor blogAuthor = ModelBuilder.generator(BlogAuthor.class);
        System.out.println(blogAuthor);
//        Mockito.when(authorDao.save(blogAuthor)).thenReturn(blogAuthor.getId());
        Assert.assertEquals(blogAuthor.getId(), authorDao.save(blogAuthor).longValue());
        Mockito.verify(authorDao, Mockito.times(1)).save(blogAuthor);
    }

}
