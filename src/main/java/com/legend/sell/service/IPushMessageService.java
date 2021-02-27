package com.legend.sell.service;

import com.legend.sell.dto.OrderMasterDTO;

/**
 * 推送消息
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/2/27
 */
public interface IPushMessageService {

    /**
     * 订单状态变更消息
     *
     * @param orderDTO
     */
    void pushOrderStatusNotice(OrderMasterDTO orderDTO);
}
