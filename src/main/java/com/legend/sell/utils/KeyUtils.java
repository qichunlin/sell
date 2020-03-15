package com.legend.sell.utils;

import java.util.Random;

public class KeyUtils {

    /**
     * 生成唯一主键
     * 格式:时间+随机数
     *
     * 多线程访问的时候也有可能出现相同的主键问题(synchronized 同步一下方法)
     * @return
     */
    public static synchronized String getUniqueKey() {
        Random random = new Random();
        //6位随机数
        Integer number = random.nextInt(900000) + 100000;
        return System.currentTimeMillis()+String.valueOf(number);
    }
}
