package com.legend.sell.repository;

import com.legend.sell.entity.OrderDetail;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Test
    public void testSave() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("1");
        orderDetail.setOrderId("1");
        orderDetail.setProductId("1");
        orderDetail.setProductName("皮蛋粥");
        orderDetail.setProductPrice(new BigDecimal(43.2));
        orderDetail.setProductQuantity(2);
        orderDetail.setProductIcon("http://xxxxx.jpg");
        OrderDetail result = orderDetailRepository.save(orderDetail);
        log.info("结果...{}", result);

        Assert.assertEquals("1", orderDetail.getDetailId());
    }


    @Test
    public void findByOrderIdTest() {
        List<OrderDetail> orderDetailList = orderDetailRepository.queryByOrderId("1");
        log.info("订单详情结果...{}", orderDetailList);
        Assert.assertNotNull(orderDetailList);
        Assert.assertNotEquals(0,orderDetailList.size());
    }
}