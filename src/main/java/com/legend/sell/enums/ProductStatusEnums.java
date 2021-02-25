package com.legend.sell.enums;

/**
 * 商品状态枚举类
 *
 * @author legend
 */
public enum ProductStatusEnums implements CodeEnums{

    UP(1, "在架"),

    DOWN(0, "下架"),
    ;

    private Integer code;
    private String message;

    @Override
    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    ProductStatusEnums(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
