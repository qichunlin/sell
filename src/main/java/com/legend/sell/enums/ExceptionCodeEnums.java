package com.legend.sell.enums;


/**
 * 异常编码信息枚举类
 *
 * @author legend
 */

public enum ExceptionCodeEnums {

    PARAM_ERROR(1 , "参数不正确"),
    PRODUCT_NOT_FOUND(10, "未找到商品"),
    PRODUCT_STOCK_ERROR(11 , "商品库存不足"),
    ORDER_NOT_FOUND(12 , "未找到订单"),
    ORDER_DETAIL_NOT_FOUND(13 , "未找到订单详情"),

    ORDER_STATUS_ERROR(14 , "订单状态不正确"),

    ORDER_STATUS_UPDATE_FAIL(15 , "订单状态更新失败"),
    ORDER_PAY_NOT_FOUND(16 , "支付状态不正确"),
    ORDER_PAY_STATUS_ERROR(17, "订单支付状态不正确"),
    ORDER_PAY_STATUS_UPDATE_FAIL(18, "订单支付状态更新失败"),

    Order_CART_EMPTY(19 , "购物车为空"),

    NOT_CURRENT_USER(20 , "不是当前用户"),

    WECHAT_MP_ERROR(21 , "微信网页授权失败"),
    WXPAY_NOTIFY_MONEY_VERIFY_ERROR(22 , "微信支付异步通知金额校验不通过"),

    ;

    private Integer code;
    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    ExceptionCodeEnums(Integer code, String message) {
        this.code = code;
        this.message = message;
    }}
