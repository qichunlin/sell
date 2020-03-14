package com.legend.sell.entity;

import com.legend.sell.enums.OrderStatusEnums;
import com.legend.sell.enums.PayStatusEnums;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单主表
 *
 * @author legend
 */
@Entity
@Data
@DynamicUpdate
public class OrderMaster {

    /**
     * 订单id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String orderId;

    /**
     * 买家姓名
     */
    private String buyerName;

    /**
     * 买家手机号码
     */
    private String buyerPhone;

    /**
     * 买家送货地址
     */
    private String buyerAddress;

    /**
     * 买家微信openid
     */
    private String buyerOpenid;

    /**
     * 订单总额
     */
    private BigDecimal orderAmount;

    /**
     * 订单状态
     */
    private Integer orderStatus = OrderStatusEnums.NEW.getCode();

    /**
     * 支付状态
     */
    private Integer payStatus = PayStatusEnums.NEW.getCode();

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
