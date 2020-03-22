package com.legend.sell.service.impl;

import com.legend.sell.dto.OrderMasterDTO;
import com.legend.sell.service.IOrderMasterService;
import com.legend.sell.service.IPayService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PayServiceImplTest {

    @Autowired
    private IPayService payService;

    @Autowired
    private IOrderMasterService orderMasterService;

    @Test
    public void create() {
        OrderMasterDTO orderMasterDTO = orderMasterService.queryOne("1584453762143291533");
        payService.create(orderMasterDTO);
    }

    @Test
    public void refund() {
        OrderMasterDTO orderMasterDTO = orderMasterService.queryOne("1584453762143291533");
        payService.refund(orderMasterDTO);
    }
}