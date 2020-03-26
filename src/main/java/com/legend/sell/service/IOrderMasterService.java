package com.legend.sell.service;

import com.legend.sell.dto.OrderMasterDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author legend
 */
public interface IOrderMasterService {

    /**
     * 创建订单
     *
     * @param orderMasterDTO
     * @return
     */
    OrderMasterDTO create(OrderMasterDTO orderMasterDTO);

    /**
     * 查找单个订单
     *
     * @param orderId
     * @return
     */
    OrderMasterDTO queryOne(String orderId);

    /**
     * 查找订单
     *
     * @param buyerOpenid
     * @param pageable
     * @return
     */
    Page<OrderMasterDTO> queryListByBuyerOpenId(String buyerOpenid, Pageable pageable);

    /**
     * 取消订单.
     *
     * @param orderMasterDTO
     * @return
     */
    OrderMasterDTO cancel(OrderMasterDTO orderMasterDTO);

    /**
     * 完结订单.
     *
     * @param orderDTO
     * @return
     */
    OrderMasterDTO finish(OrderMasterDTO orderDTO);

    /**
     * 支付订单.
     *
     * @param orderDTO
     * @return
     */
    OrderMasterDTO pay(OrderMasterDTO orderDTO);

    /**
     * 查找所有订单.
     *
     * @param
     * @param pageable
     * @return
     */
    Page<OrderMasterDTO> queryAllList( Pageable pageable);
}
