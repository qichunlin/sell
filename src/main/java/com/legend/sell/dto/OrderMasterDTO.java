package com.legend.sell.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.legend.sell.entity.OrderDetail;
import com.legend.sell.enums.OrderStatusEnums;
import com.legend.sell.enums.PayStatusEnums;
import com.legend.sell.utils.EnumsUtils;
import com.legend.sell.utils.serializer.Date2LongSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 数据传输对象
 *
 * @author legend
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderMasterDTO {
    /**
     * 订单id
     */
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
    private Integer orderStatus;

    /**
     * 支付状态
     */
    private Integer payStatus;

    /**
     * 创建时间
     */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    private List<OrderDetail> orderDetailList;

    @JsonIgnore
    public OrderStatusEnums getOrderStatusEnum() {
        return EnumsUtils.getEnumsByCode(orderStatus, OrderStatusEnums.class);
    }

    @JsonIgnore
    public PayStatusEnums getPayStatusEnum() {
        return EnumsUtils.getEnumsByCode(payStatus, PayStatusEnums.class);
    }
}
