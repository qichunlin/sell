package com.legend.sell.service.impl;

import com.legend.sell.dto.OrderMasterDTO;
import com.legend.sell.service.IOrderMasterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
public class PushMessageServiceImplTest {

    @Autowired
    private PushMessageServiceImpl pushMessageService;

    @Autowired
    private IOrderMasterService orderService;

    @Test
    public void orderStatus() throws Exception {
        //发送模板消息测试
        OrderMasterDTO orderDTO = orderService.queryOne("1");
        pushMessageService.pushOrderStatusNotice(orderDTO);
    }

}