package com.legend.sell.repository;

import com.legend.sell.entity.ProductInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;


/**
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Test
    public void saveTest() {
        ProductInfo productInfo = new ProductInfo();

//        productInfo.setProductId(4);
        productInfo.setProductName("11鱼翅");
        productInfo.setProductDescription("很香");
        productInfo.setProductIcon("https://www.baidu.com");
        productInfo.setProductPrice(new BigDecimal(13.2));
        productInfo.setProductStock(200);
        productInfo.setProductStatus(1);
        productInfo.setCategoryType(2);
        ProductInfo result = productInfoRepository.save(productInfo);

        Assert.assertNotNull(result);
        log.info("商品信息....{}",productInfo);
    }

    @Test
    public void queryByProductStatus() throws Exception {
        List<ProductInfo> productInfos = productInfoRepository.queryByProductStatus(1);
        Assert.assertNotEquals(0,productInfos.size());
        log.info("商品信息列表....{}",productInfos);
    }

}