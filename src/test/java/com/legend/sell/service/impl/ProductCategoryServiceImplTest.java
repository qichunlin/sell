package com.legend.sell.service.impl;

import com.legend.sell.entity.ProductCategory;
import com.legend.sell.service.IProductCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryServiceImplTest {

    @Autowired
    private IProductCategoryService productCategoryService;


    @Test
    public void queryOne() {
        ProductCategory productCategory = productCategoryService.queryOne(1);
        Assert.assertNotNull(productCategory);
        log.info("查询对象...{}", productCategory);
    }

    @Test
    public void queryAll() {
        List<ProductCategory> productCategories = productCategoryService.queryAll();
        Assert.assertNotEquals(0,productCategories.size());
        log.info("查询对象...{}",productCategories);
    }

    @Test
    public void queryByCategoryTypeIn() {
        List<ProductCategory> productCategories = productCategoryService.queryByCategoryTypeIn(Arrays.asList(1, 2, 3));
        Assert.assertNotEquals(0,productCategories.size());
        log.info("查询对象...{}",productCategories);
    }

    @Test
    public void save() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryType(5);
        productCategory.setCategoryName("电视");
        ProductCategory save = productCategoryService.save(productCategory);
        Assert.assertNotNull(save);
        log.info("保存对象...{}",save);
    }
}