package com.legend.sell.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * 订单详情
 *
 * @Entity 数据库字段映射成对象
 *
 * @author legend
 */
@Entity
@Data
public class OrderDetail {

    /**
     * 订单详情id
     */
    @Id
//    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private String detailId;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 商品id
     */
    private String productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品单价
     */
    private BigDecimal productPrice;

    /**
     * 商品数量
     */
    private Integer productQuantity;

    /**
     * 商品图标
     */
    private String productIcon;
}
