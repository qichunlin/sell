package com.legend.sell.service;

import com.legend.sell.dto.OrderMasterDTO;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;

/**
 * @author legend
 */
public interface IPayService {

    /**
     * 创建订单
     *
     * @param orderMasterDTO
     * @return
     */
    PayResponse create(OrderMasterDTO orderMasterDTO);

    /**
     * 异步通知
     *
     * @param notifyData
     * @return
     */
    PayResponse notify(String notifyData);

    /**
     * 退款
     *
     * @param orderMasterDTO
     */
    RefundResponse refund(OrderMasterDTO orderMasterDTO);
}
