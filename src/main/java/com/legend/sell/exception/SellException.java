package com.legend.sell.exception;

import com.legend.sell.enums.ExceptionCodeEnums;

/**
 * 异常
 * @author legend
 */
public class SellException extends RuntimeException {

    private Integer code;

    public SellException(ExceptionCodeEnums exceptionCodeEnums) {
        super(exceptionCodeEnums.getMessage());
        this.code = exceptionCodeEnums.getCode();
    }
}