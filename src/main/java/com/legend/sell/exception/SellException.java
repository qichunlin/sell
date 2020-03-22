package com.legend.sell.exception;

import com.legend.sell.enums.ExceptionCodeEnums;

/**
 * 异常
 *
 * @author legend
 */
public class SellException extends RuntimeException {

    private Integer code;

    /**
     * 添加构造方法
     *
     * @param code
     * @param message
     */
    public SellException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public SellException(ExceptionCodeEnums exceptionCodeEnums) {
        super(exceptionCodeEnums.getMessage());
        this.code = exceptionCodeEnums.getCode();
    }


}
