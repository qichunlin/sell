package com.legend.sell.service.impl;

import com.legend.sell.dto.OrderMasterDTO;
import com.legend.sell.enums.ExceptionCodeEnums;
import com.legend.sell.exception.SellException;
import com.legend.sell.service.IBuyerOrderService;
import com.legend.sell.service.IOrderMasterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author legend
 */
@Slf4j
@Service
public class BuyerOrderServiceImpl implements IBuyerOrderService {

    @Autowired
    private IOrderMasterService orderMasterService;

    @Override
    public OrderMasterDTO queryOne(String openid, String orderId) {
        return checkOwner(openid, orderId);
    }

    @Override
    public OrderMasterDTO cancel(String openid, String orderId) {
        //校验个人
        OrderMasterDTO orderDTO = this.checkOwner(openid, orderId);
        if (orderDTO == null) {
            log.error("【取消订单】订单不存在，openid={}，orderId={}", openid, orderId);
            throw new SellException(ExceptionCodeEnums.ORDER_NOT_FOUND);
        }

        return orderMasterService.cancel(orderDTO);
    }

    /**
     * 校验个人所有的
     *
     * @param openid
     * @param orderId
     * @return
     */
    private OrderMasterDTO checkOwner(String openid, String orderId) {
        OrderMasterDTO orderMasterDTO = orderMasterService.queryOne(orderId);

        if (orderMasterDTO == null) {
            return null;
        }

        if (!openid.equals(orderMasterDTO.getBuyerOpenid())) {
            log.error("【查找订单】openid不一致，openid={}，orderId={}", openid, orderId);
            throw new SellException(ExceptionCodeEnums.NOT_CURRENT_USER);
        }

        return orderMasterDTO;
    }

}
