package com.legend.sell.enums;

import lombok.Getter;

/**
 * 结果编码枚举
 *
 * @author legend
 */
@Getter
public enum ResultCodeEnums {
    OK(0, "成功"),
    ERROR(-1, "失败");

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 状态码信息
     */
    private String msg;

    ResultCodeEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
