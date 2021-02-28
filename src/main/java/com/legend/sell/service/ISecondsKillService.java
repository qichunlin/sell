package com.legend.sell.service;

/**
 * 秒杀
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/2/28
 */
public interface ISecondsKillService {

    /**
     * 查询秒杀特价商品信息
     *
     * @param productId
     * @return
     */
    String querySecondsKillProductInfo(String productId);

    /**
     * 秒杀的方法
     *
     * @param productId
     */
    void orderProductMockDiffUser(String productId);

    /**
     * 秒杀的方法
     *
     * @param productId
     */
    void orderProductMockDiffUser2(String productId);
}
