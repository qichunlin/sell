package com.legend.sell.service.impl;

import com.legend.sell.dto.OrderMasterDTO;
import com.legend.sell.entity.OrderDetail;
import com.legend.sell.enums.OrderStatusEnums;
import com.legend.sell.enums.PayStatusEnums;
import com.legend.sell.service.IOrderMasterService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterServiceImplTest {

    @Autowired
    private IOrderMasterService orderMasterService;

    private static final String BUYER_OPENID = "abc123";

//    private static final String ORDER_ID = "1584266903383596286";
    private static final String ORDER_ID = "2";

    @Test
    public void create() {
        OrderMasterDTO orderMasterDTO = new OrderMasterDTO();
        orderMasterDTO.setBuyerAddress("广东深圳");
        orderMasterDTO.setBuyerPhone("10086");
        orderMasterDTO.setBuyerOpenid(BUYER_OPENID);
        orderMasterDTO.setBuyerName("Legend");

        //购物车
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("1");
        orderDetail.setProductQuantity(10);
        orderDetailList.add(orderDetail);

        orderMasterDTO.setOrderDetailList(orderDetailList);
        OrderMasterDTO orderMasterDTOSave = orderMasterService.create(orderMasterDTO);
        log.info("[创建订单]订单数据Result......{}",orderMasterDTOSave);
        Assert.assertNotNull(orderMasterDTOSave);
    }

    @Test
    public void queryOne() {
        OrderMasterDTO orderMasterDTO = orderMasterService.queryOne(ORDER_ID);
        log.info("查询单个订单数据....{}",orderMasterDTO);
        Assert.assertEquals(ORDER_ID,orderMasterDTO.getOrderId());
    }

    @Test
    public void queryListByBuyerOpenId() {
        PageRequest pageRequest = new PageRequest(0,1);
        Page<OrderMasterDTO> orderMasterDTOPage = orderMasterService.queryListByBuyerOpenId(BUYER_OPENID, pageRequest);
        Assert.assertNotNull(orderMasterDTOPage);
        Assert.assertEquals(0,orderMasterDTOPage.getTotalElements());
    }

    @Test
    public void cancel() {
        OrderMasterDTO orderMasterDTO = orderMasterService.queryOne(ORDER_ID);
        OrderMasterDTO result = orderMasterService.cancel(orderMasterDTO);
        Assert.assertEquals(OrderStatusEnums.CANCEL.getCode(),result.getOrderStatus());
    }

    @Test
    public void finish() {
        OrderMasterDTO orderMasterDTO = orderMasterService.queryOne(ORDER_ID);
        OrderMasterDTO result = orderMasterService.finish(orderMasterDTO);
        Assert.assertEquals(OrderStatusEnums.FINISH.getCode(),result.getOrderStatus());
    }

    @Test
    public void pay() {
        OrderMasterDTO orderMasterDTO = orderMasterService.queryOne(ORDER_ID);
        OrderMasterDTO result = orderMasterService.pay(orderMasterDTO);
        Assert.assertEquals(PayStatusEnums.FINISH.getCode(),result.getPayStatus());
    }

    @Test
    public void queryList() {
    }
}