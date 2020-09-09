package com.snackpub.core.wxlsjg.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.databind.cfg.SerializerFactoryConfig;
import com.snackpub.core.wxlsjg.model.menu.ClickButton;
import com.snackpub.core.wxlsjg.model.menu.ViewButton;
import com.snackpub.core.wxlsjg.props.WxProperties;
import lombok.SneakyThrows;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author snackpub
 * @date 2020/9/8
 */
@Service
public class WxSnackpubService {


    private WxProperties wxProperties;

    @Autowired
    public WxSnackpubService(WxProperties wxProperties) {
        this.wxProperties = wxProperties;
    }


    @SneakyThrows
    public void menuCreate() {

        ClickButton click = new ClickButton("点我看看", "click", "text");

        ViewButton view = new ViewButton("微信开发", "view", "https://github.com/snackpub/snack-seata/tree/master/snack-core-wx-lsjg");
        ViewButton view2 = new ViewButton("工作流开发", "view", "https://github.com/snackpub/snack-seata/tree/master/snack-core-activiti");

        JSONArray subButtonArr = new JSONArray();
        subButtonArr.add(click);
        subButtonArr.add(view);
        subButtonArr.add(view2);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "gitHub");
        jsonObject.put("sub_button", subButtonArr);


        JSONArray button = new JSONArray();
        button.add(view);
        button.add(jsonObject);
        button.add(click);

        JSONObject menuJson = new JSONObject();
        menuJson.put("button", button);
        // 禁用循环引用发现
        String menuData = JSON.toJSONString(menuJson, SerializerFeature.DisableCircularReferenceDetect);
        System.err.println(menuData);

        String token = wxProperties.getToken();

        String url = wxProperties.getMenuUrl() + token;
        HttpClient hc = new HttpClient();
        PostMethod pm = new PostMethod(url);
        RequestEntity re = new StringRequestEntity(menuData, "application/json", "UTF-8");
        pm.setRequestEntity(re);
        hc.executeMethod(pm);
        String respBody = pm.getResponseBodyAsString();
        System.err.println(respBody);


    }

}
