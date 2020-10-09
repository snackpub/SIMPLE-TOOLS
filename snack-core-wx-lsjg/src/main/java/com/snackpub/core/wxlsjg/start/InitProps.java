package com.snackpub.core.wxlsjg.start;

import com.snackpub.core.wxlsjg.constant.GlobalConstant;
import com.snackpub.core.wxlsjg.props.WxProperties;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author snackpub
 * @date 2020/9/14
 */
@Slf4j
public class InitProps {

    /**
     * 初始化全局properties
     */
    public synchronized static void init() {
        if (GlobalConstant.globalProperties == null) {
            GlobalConstant.globalProperties = new Properties();
        }
    }


    /**
     * 保存.properties 文件的数据复制到系统一份
     *
     * @param propertiesFileName 属性文件名称
     * @throws IOException 读流异常
     */
    public static void putSystemProperty(String propertiesFileName) throws IOException {
        InputStream inStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(propertiesFileName);
        if (inStream != null) {
            Properties prop = new Properties();
            prop.load(inStream);
            prop.keySet().forEach(k -> {
                String key = (String) k;
                System.setProperty(key, prop.getProperty(key));
            });
            log.info("Load system property finished: [{}] ", propertiesFileName);
        } else {
            log.warn("Load system property fail, Can't find file: [{}]", propertiesFileName);
        }
    }


}
