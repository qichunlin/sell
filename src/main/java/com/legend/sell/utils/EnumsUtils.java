package com.legend.sell.utils;

import com.legend.sell.enums.CodeEnums;


/**
 * 枚举工具类
 *
 * @author legend
 */
public class EnumsUtils {

    private EnumsUtils() {
    }

    public static <T extends CodeEnums> T getEnumsByCode(Integer code, Class<T> enums) {
        for (T e : enums.getEnumConstants()) {
            if (e.getCode() == code) {
                return e;
            }
        }
        return null;
    }
}
