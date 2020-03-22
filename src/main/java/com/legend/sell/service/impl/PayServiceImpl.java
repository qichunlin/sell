package com.legend.sell.service.impl;

import com.legend.sell.dto.OrderMasterDTO;
import com.legend.sell.service.IPayService;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PayServiceImpl implements IPayService {

    private static final String ORDER_NAME = "微信点餐";

    @Autowired
    private BestPayServiceImpl bestPayService;

    @Override
    public PayResponse create(OrderMasterDTO orderMasterDTO) {
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderMasterDTO.getBuyerOpenid());
        payRequest.setOrderId(orderMasterDTO.getOrderId());
        payRequest.setOrderAmount(orderMasterDTO.getOrderAmount().doubleValue());
        payRequest.setOrderName(ORDER_NAME);
        //微信公众账号支付
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_MP);
        log.info("微信支付 payRequest={}", payRequest);

        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("微信支付 payResponse={}", payResponse);
        return payResponse;
    }
}
