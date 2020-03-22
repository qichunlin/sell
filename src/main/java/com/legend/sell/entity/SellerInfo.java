package com.legend.sell.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 卖家信息表
 *
 * @author legend
 */
@Data
@Entity
public class SellerInfo {

    /**
     * 卖家id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String sellerId;

    /**
     * 卖家姓名.
     */
    private String username;

    /**
     * 卖家登录密码.
     */
    private String password;

    /**
     * 卖家微信openid.
     */
    private String openid;

}
