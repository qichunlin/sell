package com.legend.sell.service.impl;

import com.legend.sell.entity.SellerInfo;
import com.legend.sell.repository.SellerInfoRepository;
import com.legend.sell.service.ISellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/2/27
 */
@Service
public class SellerServiceImpl implements ISellerService {

    @Autowired
    private SellerInfoRepository repository;

    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return repository.findByOpenid(openid);
    }
}
