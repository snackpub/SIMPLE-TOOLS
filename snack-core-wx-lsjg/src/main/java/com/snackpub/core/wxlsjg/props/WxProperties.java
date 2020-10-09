package com.snackpub.core.wxlsjg.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * wechat配置文件
 *
 * @author snackpub
 * @date 2020/9/9
 */
@Component
@ConfigurationProperties("wx.snack")
@PropertySource("classpath:wxApplication.properties")
public class WxProperties {

    @Setter
    @Getter
    private String grantType;

    @Setter
    @Getter
    private String appid;

    @Setter
    @Getter
    private String secret;

    @Setter
    @Getter
    private String menuCreate;

    @Setter
    @Getter
    private String tokenUrl;

    @Setter
    @Getter
    private String mediaUrl;

    @Setter
    @Getter
    private String addMaterial;

    @Setter
    @Getter
    private String menuSelect;


}
