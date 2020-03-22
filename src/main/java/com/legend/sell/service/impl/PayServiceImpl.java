package com.legend.sell.service.impl;

import com.legend.sell.dto.OrderMasterDTO;
import com.legend.sell.enums.ExceptionCodeEnums;
import com.legend.sell.exception.SellException;
import com.legend.sell.service.IOrderMasterService;
import com.legend.sell.service.IPayService;
import com.legend.sell.utils.JsonUtils;
import com.legend.sell.utils.MathUtils;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author legend
 */
@Slf4j
@Service
public class PayServiceImpl implements IPayService {

    private static final String ORDER_NAME = "微信点餐";

    @Autowired
    private BestPayServiceImpl bestPayService;

    @Autowired
    private IOrderMasterService orderMasterService;

    @Override
    public PayResponse create(OrderMasterDTO orderMasterDTO) {
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderMasterDTO.getBuyerOpenid());
        payRequest.setOrderId(orderMasterDTO.getOrderId());
        payRequest.setOrderAmount(orderMasterDTO.getOrderAmount().doubleValue());
        payRequest.setOrderName(ORDER_NAME);
        //微信公众账号支付
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_MP);
        log.info("微信支付 payRequest={}", JsonUtils.toJson(payRequest));

        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("微信支付 payResponse={}", JsonUtils.toJson(payResponse));
        return payResponse;
    }

    @Override
    public PayResponse notify(String notifyData) {
        //支付的异步通知步骤
        //1.验证签名 (bestPay做好了)
        //2.支付的状态 (bestPay做好了)
        //3.支付的金额 (自定义开发)
        //4.支付人(下单人==支付人  看业务需求)

        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("微信支付 异步通知 payResponse={}", JsonUtils.toJson(payResponse));


        //查询订单
        OrderMasterDTO orderMasterDTO = orderMasterService.queryOne(payResponse.getOrderId());
        //判断订单是否存在
        if (orderMasterDTO == null) {
            log.error("微信支付 异步通知,订单不存在,orderId={}", payResponse.getOrderId());
            throw new SellException(ExceptionCodeEnums.ORDER_NOT_FOUND);
        }

        //判断金额是否一致 (0.10  0.1 其实是一致的)
        //这里需要注意BigDecimal 不能跟Double值比较
        //if (orderMasterDTO.getOrderAmount().compareTo(new BigDecimal(payResponse.getOrderAmount())) != 0) { //这里出现了精度问题
        if (MathUtils.isEqual(orderMasterDTO.getOrderAmount().doubleValue(), payResponse.getOrderAmount().doubleValue())) {
            log.error("微信支付 异步通知,订单金额不一致,orderId={},微信通知金额{},,系统金额{}",
                    payResponse.getOrderId(),
                    payResponse.getOrderAmount(),
                    orderMasterDTO.getOrderAmount());
            throw new SellException(ExceptionCodeEnums.WXPAY_NOTIFY_MONEY_VERIFY_ERROR);
        }
        //微信那边一直会调用我们的异步通知方法  所以会一直调用pay方法
        //修改订单的支付状态
        orderMasterService.pay(orderMasterDTO);
        return payResponse;
    }
}
