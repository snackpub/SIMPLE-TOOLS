package com.snackpub.core.wxlsjg.config;

import com.snackpub.core.wxlsjg.constant.SnackConstant;
import com.snackpub.core.wxlsjg.props.WxProperties;
import com.snackpub.core.wxlsjg.start.InitProps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * bean 装配
 *
 * @author snackpub
 * @date 2020/9/11
 */
@Slf4j
@Configuration
public class BeanConfiguration {

    public BeanConfiguration() throws IOException {
        // 利用构造函数初始化globalProperties,避免TokenJob bean加载时为null的问题
        InitProps.init();
        InitProps.putSystemProperty(SnackConstant.APP_PROPERTIES_FILENAME);
    }

    @Bean
    public HttpClient snackHttpClient() {
        return new HttpClient();
    }
}
