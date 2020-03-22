package com.legend.sell.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Json工具类
 *
 * @author legend
 */
public class JsonUtils {

    /**
     * 对象转化成json
     *
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();

        Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }
}
