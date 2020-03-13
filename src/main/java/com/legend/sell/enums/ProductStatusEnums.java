package com.legend.sell.enums;

/**
 * 商品状态枚举类
 *
 * @author legend
 */
public enum ProductStatusEnums {

    UP(1, "在架"),

    DOWN(0, "下架"),
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

    ProductStatusEnums(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
