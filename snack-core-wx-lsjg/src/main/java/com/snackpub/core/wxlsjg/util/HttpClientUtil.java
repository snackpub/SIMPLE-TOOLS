package com.snackpub.core.wxlsjg.util;

import com.snackpub.core.wxlsjg.constant.SnackConstant;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import java.util.Map;
import java.util.Set;

/**
 * @author snackpub
 * @date 2020/9/10
 */
@UtilityClass
public class HttpClientUtil {


    @SneakyThrows
    public String sendGet(String url) {
        HttpClient httpClient = new HttpClient();
        GetMethod pm = new GetMethod(url);
        httpClient.executeMethod(pm);
        String respBody = pm.getResponseBodyAsString();
        return respBody;
    }

    @SneakyThrows
    public String sendGet(String reqUrl, Map<String, String> params) {
        HttpClient httpClient = new HttpClient();
        String url = builderUrl(reqUrl, params);
        GetMethod pm = new GetMethod(url);
        httpClient.executeMethod(pm);
        String respBody = pm.getResponseBodyAsString();
        return respBody;
    }


    @SneakyThrows
    public String sendPost(String url, String bodyJson) {
        HttpClient hc = new HttpClient();
        PostMethod pm = new PostMethod(url);
        RequestEntity re = new StringRequestEntity(bodyJson, SnackConstant.CONTENT_TYPE, SnackConstant.UTF_8);
        pm.setRequestEntity(re);
        hc.executeMethod(pm);
        String respBody = pm.getResponseBodyAsString();
        return respBody;
    }

    /**
     * 构建get方式的url
     *
     * @param reqUrl url地址
     * @param params 查询参数
     * @return url
     */
    public String builderUrl(String reqUrl, Map<String, String> params) {
        StringBuilder query = new StringBuilder();
        Set<String> set = params.keySet();
        for (String key : set) {
            query.append(String.format("%s=%s&", key, params.get(key)));
        }
        return reqUrl + "?" + query.toString();
    }


}
