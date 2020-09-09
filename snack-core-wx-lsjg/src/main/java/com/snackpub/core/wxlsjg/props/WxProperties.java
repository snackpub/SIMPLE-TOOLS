package com.snackpub.core.wxlsjg.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 配置文件
 *
 * @author snackpub
 * @date 2020/9/9
 */
@ConfigurationProperties("wx.sanck")
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
    private String menuUrl;

    @Setter
    @Getter
    private String token;


}
