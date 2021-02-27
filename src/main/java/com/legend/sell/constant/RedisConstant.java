package com.legend.sell.constant;

/**
 * redis常量
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/2/27
 */
public interface RedisConstant {

    String TOKEN_PREFIX = "token_%s";

    /**
     * 2小时
     */
    Integer EXPIRE = 7200;
}
