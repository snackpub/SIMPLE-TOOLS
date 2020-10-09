package com.snackpub.core.wxlsjg.util;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.snackpub.core.wxlsjg.Token;
import com.snackpub.core.wxlsjg.constant.SnackConstant;
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
     * @return token
     */
    public Token getToken() {
        Token token = null;
        String grantType = PropsUtil.getPropsKey(SnackConstant.WX_PROPS_GRANT_TYPE);
        String appId = PropsUtil.getPropsKey(SnackConstant.WX_PROPS_APPID);
        String appSecret = PropsUtil.getPropsKey(SnackConstant.WX_PROPS_SECRET);
        String tokenUrl = PropsUtil.getPropsKey(SnackConstant.WX_TOKEN_URL);

        Map<String, String> param = new HashMap<>(3);
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
                log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInteger("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return token;
    }
}
