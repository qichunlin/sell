package com.legend.sell.vo;


import lombok.Data;

import java.io.Serializable;

/**
 * http请求返回给前端最外层对象
 *
 * @author legend
 */
@Data
public class ResultVO<T> implements Serializable {

    /**
     * 错误码
     */
    private Integer code;
    /**
     * 提示信息
     */
    private String msg;

    /**
     * 具体内容
     */
    private T data;
}
