package com.legend.sell.service;

import com.legend.sell.entity.SellerInfo;

/**
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/2/27
 */
public interface ISellerService {

    /**
     * 通过openid查询卖家端信息
     *
     * @param openid
     * @return
     */
    SellerInfo findSellerInfoByOpenid(String openid);
}
