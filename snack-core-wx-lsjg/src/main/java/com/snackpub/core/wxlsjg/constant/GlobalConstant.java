package com.snackpub.core.wxlsjg.constant;

import java.util.Properties;

/**
 * 持久化全局常量
 *
 * @author snackpub
 * @date 2020/9/13
 */
public class GlobalConstant {
    public static Properties globalProperties;

    /**
     * 从Properties上下文中获取token
     *
     * @param key 键
     * @return str
     */
    public static String getKey(String key) {
        return (String) globalProperties.get(key);
    }
}
