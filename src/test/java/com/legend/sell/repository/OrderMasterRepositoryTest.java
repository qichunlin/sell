package com.legend.sell.repository;

import com.legend.sell.entity.OrderMaster;
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

import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    private final String OPENID = "abc123";

    @Test
    public void testSave(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("1");
        orderMaster.setBuyerAddress("广深");
        orderMaster.setBuyerName("legend");
        orderMaster.setBuyerPhone("10086");
        orderMaster.setBuyerOpenid(OPENID);
        orderMaster.setOrderAmount(new BigDecimal(2000));

        OrderMaster orderMasterSave = orderMasterRepository.save(orderMaster);
        Assert.assertNotNull(orderMasterSave);
        log.info("订单.....{}",orderMasterSave);

    }

    @Test
    public void testQueryByBuyerOpenid(){
        PageRequest pageRequest = new PageRequest(0,1);
        Page<OrderMaster> orderMasters = orderMasterRepository.queryByBuyerOpenid(OPENID, pageRequest);
        log.info("orderMasters...{}",orderMasters.getTotalElements());
    }

}