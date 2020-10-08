package com.snackpub.core.wxlsjg.config;

import com.snackpub.core.wxlsjg.props.WxProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * 启用WX配置
 *
 * @author snackpub
 * @date 2020/9/9
 */
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableConfigurationProperties({WxProperties.class})
public class WxConfiguration {

}
