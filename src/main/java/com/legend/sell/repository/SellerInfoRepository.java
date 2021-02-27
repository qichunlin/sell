package com.legend.sell.repository;


import com.legend.sell.entity.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/2/27
 */
public interface SellerInfoRepository extends JpaRepository<SellerInfo, String> {

    SellerInfo findByOpenid(String openid);
}
