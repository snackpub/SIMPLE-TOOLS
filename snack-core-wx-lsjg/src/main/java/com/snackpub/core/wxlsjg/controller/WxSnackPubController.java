package com.snackpub.core.wxlsjg.controller;

import com.snackpub.core.wxlsjg.model.message.ImageMessage;
import com.snackpub.core.wxlsjg.model.message.TextMessage;
import com.snackpub.core.wxlsjg.model.message.TopicMessage;
import com.snackpub.core.wxlsjg.service.WxSnackpubService;
import com.snackpub.core.wxlsjg.util.CheckUtil;
import com.snackpub.core.wxlsjg.util.MessageUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @author snackpub
 * @date 2020/9/8
 */
@Slf4j
@RestController
@RequestMapping("/wx")
public class WxSnackPubController {

    @Autowired
    private WxSnackpubService wxSnackpubService;

    @SneakyThrows
    @GetMapping("/wxt")
    public void wxT(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "signature") String signature,
            @RequestParam(value = "timestamp") String timestamp,
            @RequestParam(value = "nonce") String nonce,
            @RequestParam(value = "echostr") String echostr) {
        PrintWriter out = response.getWriter();
        if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
            //如果校验成功，将得到的随机字符串原路返回
            out.print(echostr);
            out.close();
        } else {
            log.info("非法请求，请注意！");
        }
    }

    @PostMapping("/wxt")
    @SneakyThrows
    public void tMsg(HttpServletRequest request, HttpServletResponse response) {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/xml;charset=utf-8");
        Map<String, String> map = MessageUtil.xmlToMap(request);
        PrintWriter out = response.getWriter();
        String toUserName = map.get("ToUserName");
        String fromUserName = map.get("FromUserName");
        String msgType = map.get("MsgType");
        String content = map.get("Content");

        String msgId = map.get("MsgId");

        String message = null;
        // 对文本消息进行处理
        if ("text".equals(msgType)) {
            TextMessage text = new TextMessage();
            // 发送和回复是反向的
            text.setFromUserName(toUserName);
            text.setToUserName(fromUserName);
            text.setMsgType("text");
            text.setCreateTime(System.currentTimeMillis());
            text.setContent("你发送的消息是：" + content);
            message = MessageUtil.textMessageToXml(text);
            System.out.println(message);
        } else if ("image".equals(msgType)) {
            String mediaId = map.get("MediaId");
            String picUrl = map.get("PicUrl");
            ImageMessage image = new ImageMessage();
            image.setMediaId(mediaId);
            image.setPicUrl(picUrl);
            image.setFromUserName(fromUserName);
            image.setMsgType("image");
            image.setCreateTime(System.currentTimeMillis());
            message = MessageUtil.imageMessageToXml(image);
            System.out.println(message);
        } else if ("event".equals(msgType)) {
            // 事件推送
            String event = map.get("Event");
            TopicMessage topic = new TopicMessage();
            topic.setEvent(event);
            topic.setFromUserName(fromUserName);
            topic.setMsgType("event");
            topic.setCreateTime(System.currentTimeMillis());
            if ("subscribe".equals(event)) {
                message = MessageUtil.topicEventMessageToXml(topic);
                log.info("订阅");
            } else if ("unsubscribe".equals(event)) {
                message = MessageUtil.topicEventMessageToXml(topic);
                log.info("取消订阅");
            }

            // 点击菜单拉取消息时的事件推送
            if ("CLICK".equals(event)) {
                String eventKey = map.get("EventKey");
                System.err.println("EventKey: "+eventKey);
            }

            System.out.println(message);
        }
        // 将回应发送给微信服务器
        out.print(message);
        out.close();
    }

    @GetMapping("/menu")
    public void menuCreate(HttpServletRequest request, HttpServletResponse response) {
        wxSnackpubService.menuCreate();
    }


}
