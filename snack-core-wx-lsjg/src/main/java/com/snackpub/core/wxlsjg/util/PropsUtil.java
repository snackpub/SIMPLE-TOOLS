package com.snackpub.core.wxlsjg.util;

import lombok.experimental.UtilityClass;


/**
 * @author snackpub
 * @date 2020/10/8
 */
@UtilityClass
public class PropsUtil {


    public static String getPropsKey(String key) {
        String property = System.getProperty(key);
        return property;
    }

}
