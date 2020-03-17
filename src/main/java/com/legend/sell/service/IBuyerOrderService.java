package com.legend.sell.service;

import com.legend.sell.dto.OrderMasterDTO;

/**
 * @author legend
 */
public interface IBuyerOrderService {

    /**
     * 查找订单
     *
     * @param openid
     * @param orderId
     * @return
     */
    OrderMasterDTO queryOne(String openid, String orderId);

    /**
     * 取消订单
     *
     * @param openid
     * @param orderId
     * @return
     */
    OrderMasterDTO cancel(String openid, String orderId);
}
