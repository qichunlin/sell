package com.legend.sell.service.impl;

import com.legend.sell.entity.ProductInfo;
import com.legend.sell.enums.ProductStatusEnums;
import com.legend.sell.service.IProductInfoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author legend
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceImplTest {

    @Autowired
    private IProductInfoService productService;

    @Test
    public void queryOne() throws Exception {
        ProductInfo productInfo = productService.queryOne(1);
//        Assert.assertEquals(1, productInfo.getProductId());
        log.info("商品信息....{}",productInfo);
    }

    @Test
    public void queryUpAll() throws Exception {
        List<ProductInfo> productInfos = productService.queryUpAll();
        Assert.assertNotEquals(0, productInfos.size());
        log.info("商品信息....{}",productInfos);
    }

    @Test
    public void queryByPage() throws Exception {
        PageRequest pageRequest = new PageRequest(0, 2);
        Page<ProductInfo> productInfos = productService.queryByPage(pageRequest);
        Assert.assertNotNull(productInfos);
        log.info("商品信息....{}",productInfos);
    }

    @Test
    public void save() throws Exception {
        ProductInfo productInfo = new ProductInfo();

//        productInfo.setProductId(1);
        productInfo.setProductName("烤鱼");
        productInfo.setProductDescription("超级好吃");
        productInfo.setProductIcon("http://xxxxx.jpg");
        productInfo.setProductPrice(new BigDecimal(3.2));
        productInfo.setProductStock(100);
        productInfo.setCategoryType(3);
        productInfo.setProductStatus(ProductStatusEnums.UP.getCode());

        ProductInfo result = productService.save(productInfo);

        Assert.assertNotNull(result);
        log.info("商品信息....{}",result);
    }

}