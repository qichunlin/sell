package com.legend.sell.utils;

/**
 * 数字工具类
 *
 * @author legend
 */
public class MathUtils {

    private static final Double MONEY_RANGE = 0.001;

    private MathUtils() {
    }

    /**
     * 比较两个金额是否相等
     *
     * @param num1
     * @param num2
     * @return
     */
    public static Boolean isEqual(Double num1, Double num2) {
        Double result = Math.abs(num1 - num2);
        if (result < MONEY_RANGE) {
            return true;
        } else {
            return false;
        }

    }
}
