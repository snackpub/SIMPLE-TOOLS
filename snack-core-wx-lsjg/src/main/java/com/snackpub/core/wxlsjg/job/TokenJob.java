package com.snackpub.core.wxlsjg.job;

import com.snackpub.core.wxlsjg.Token;
import com.snackpub.core.wxlsjg.constant.GlobalConstant;
import com.snackpub.core.wxlsjg.props.WxProperties;
import com.snackpub.core.wxlsjg.util.TokenUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 通过定时任务，每5分钟获取一次token
 *
 * @author snackpub
 * @date 2020/9/13
 */
@Slf4j
@Configuration
@EnableScheduling
@AllArgsConstructor
public class TokenJob {

    private WxProperties wxProperties;

    @Scheduled(cron = "0 0/20 * * * ?")
    public void execute() {
        Token token = TokenUtil.getToken(wxProperties);
        // 保存Token
        GlobalConstant.globalProperties.put("access_token", token.getToken());
        log.info("token=" + token.getToken());
    }

}
