package com.snackpub.core.wxlsjg.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.snackpub.core.tools.utils.SpringUtil;
import com.snackpub.core.wxlsjg.Token;
import com.snackpub.core.wxlsjg.props.WxProperties;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * token 工具类
 *
 * @author snackpub
 * @date 2020/9/13
 */
@UtilityClass
@Slf4j
public class TokenUtil {

    /**
     * 获取Token凭证
     *
     * @return
     */
    public Token getToken(WxProperties wxProperties) {
        Token token = null;
        String tokenUrl = wxProperties.getTokenUrl();
        String appId = wxProperties.getAppid();
        String appSecret = wxProperties.getSecret();
        String grantType = wxProperties.getGrantType();

        Map<String, String> param = new HashMap<>();
        param.put("grant_type", grantType);
        param.put("appid", appId);
        param.put("secret", appSecret);
        String url = HttpClientUtil.builderUrl(tokenUrl, param);

        String respBody = HttpClientUtil.sendGet(url);

        JSONObject jsonObject = JSONObject.parseObject(respBody);
        if (jsonObject != null) {
            try {
                token = new Token()
                        .setToken(jsonObject.getString("access_token"))
                        .setExpire(jsonObject.getInteger("expires_in"));
            } catch (JSONException e) {
                token = null;
                log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInteger("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return token;
    }

    public Token getToken() {
        WxProperties wxProperties = SpringUtil.getBean("wxProperties", WxProperties.class);
        Token token = null;
        String tokenUrl = wxProperties.getTokenUrl();
        String appId = wxProperties.getAppid();
        String appSecret = wxProperties.getSecret();
        String grantType = wxProperties.getGrantType();

        Map<String, String> param = new HashMap<>();
        param.put("grant_type", grantType);
        param.put("appid", appId);
        param.put("secret", appSecret);
        String url = HttpClientUtil.builderUrl(tokenUrl, param);

        String respBody = HttpClientUtil.sendGet(url);

        JSONObject jsonObject = JSONObject.parseObject(respBody);
        if (jsonObject != null) {
            try {
                token = new Token()
                        .setToken(jsonObject.getString("access_token"))
                        .setExpire(jsonObject.getInteger("expires_in"));
            } catch (JSONException e) {
                token = null;
                log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInteger("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return token;
    }


}
