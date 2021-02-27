package com.legend.sell.service.impl;

import com.legend.sell.entity.ProductCategory;
import com.legend.sell.mapper.ProductCategoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/2/27
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductCategoryMapperTest {

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @Test
    public void insertProductCategoryByMapTest() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("category_name", "A");
        map.put("category_type", 88);
        log.info("当前Map集合长度为:{}", map.size());
        productCategoryMapper.insertProductCategoryByMap(map);
    }

    @Test
    public void insertByProductCategoryTest() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryType(33333);
        productCategory.setCategoryName("2222222");
        productCategoryMapper.insertByProductCategory(productCategory);
    }

    @Test
    public void findProductCategoryTest() {
        ProductCategory productCategory = productCategoryMapper.findProductCategory(11);
        log.info("dddd:{}", productCategory);
    }

}
