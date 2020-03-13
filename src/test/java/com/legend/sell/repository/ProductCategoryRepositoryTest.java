package com.legend.sell.repository;

import com.legend.sell.entity.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository repository;


    @Test
    @Transactional
    public void queryByCategoryTypeInTest(){
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(11);
        List<ProductCategory> productCategories = repository.queryByCategoryTypeIn(list);
        Assert.assertNotNull(productCategories);
        Assert.assertNotEquals(0,productCategories.size());
    }

    @Test
    @Transactional
    public void saveNotNullTest(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryId(3);
        productCategory.setCategoryName("哇哈哈");
        productCategory.setCategoryType(1);
        ProductCategory result = repository.save(productCategory);
        Assert.assertNotNull(result);
        //Assert.assertNotEquals(null,result);//效果一样

    }

    @Test
    public void saveTest(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("legend");
        productCategory.setCategoryType(1);
        ProductCategory save = repository.save(productCategory);
        System.out.println(save);
        //4-2开始
    }

    @Test
    public void findOneTest(){
        Optional<ProductCategory> byId = repository.findById(1);
        System.out.println(byId);
    }
}