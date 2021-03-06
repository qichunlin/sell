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
import java.util.List;
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
        log.info("查询商品分类通过类型:{}", productCategory);
    }

    @Test
    public void findProductCategoryByNameTest() {
        List<ProductCategory> productCategory = productCategoryMapper.findProductCategoryByName("2222222");
        log.info("查询商品分类通过名字:{}", productCategory);
    }

    @Test
    public void updateByProductCategoryTest() {
        int result = productCategoryMapper.updateByProductCategory(1,"2222222");
        log.info("更新商品分类通过id:{}", result);
    }

    @Test
    public void deleteProductCategoryTest() {
        int result = productCategoryMapper.deleteProductCategory(1);
        log.info("删除商品分类通过id:{}", result);
    }

    @Test
    public void findAllByXmlTest() {
        List<ProductCategory> productCategory = productCategoryMapper.findAllByXml();
        log.info("查询所有商品分类:{}", productCategory);
    }

    @Test
    public void insertProductCategoryByXmlTest() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryType(90);
        productCategory.setCategoryName("测试xml");
        productCategoryMapper.insertProductCategoryByXml(productCategory);
    }

}
