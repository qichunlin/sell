package com.legend.sell.exception;

/**
 * 举例返回给银行响应异常 status改变
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/2/27
 */
public class ResponseBankException extends RuntimeException {
    public ResponseBankException(String message) {
        super(message);
    }
}
