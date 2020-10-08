package com.snackpub.core.wxlsjg.controller;

import com.alibaba.fastjson.JSONObject;
import com.snackpub.core.wxlsjg.constant.GlobalConstant;
import com.snackpub.core.wxlsjg.constant.MsgTypeConstant;
import com.snackpub.core.wxlsjg.model.event.PicSysPhotoEvent;
import com.snackpub.core.wxlsjg.model.message.Image;
import com.snackpub.core.wxlsjg.model.message.ImageMessage;
import com.snackpub.core.wxlsjg.model.message.TextMessage;
import com.snackpub.core.wxlsjg.model.message.TopicMessage;
import com.snackpub.core.wxlsjg.props.WxProperties;
import com.snackpub.core.wxlsjg.service.WxMediaUploadService;
import com.snackpub.core.wxlsjg.service.WxSnackpubService;
import com.snackpub.core.wxlsjg.util.CheckUtil;
import com.snackpub.core.wxlsjg.util.MediaUploadUtil;
import com.snackpub.core.wxlsjg.util.MessageUtil;
import com.snackpub.core.wxlsjg.util.TokenUtil;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
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
@AllArgsConstructor
public class WxSnackPubController {

    private WxSnackpubService wxSnackpubService;
    private WxProperties wxProperties;

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
            log.error("非法请求，请注意！");
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
        String createTime = map.get("CreateTime");

        String msgId = map.get("MsgId");

        String message = null;
        // 对文本消息进行处理
        if (MsgTypeConstant.RESP_MESSAGE_TYPE_TEXT.equals(msgType)) {
            TextMessage text = new TextMessage();
            // 发送和回复是反向的
            text.setFromUserName(toUserName);
            text.setToUserName(fromUserName);
            text.setMsgType(MsgTypeConstant.RESP_MESSAGE_TYPE_TEXT);
            text.setCreateTime(System.currentTimeMillis());
            text.setContent("你发送的消息是：" + content);
            message = MessageUtil.textMessageToXml(text);
            System.err.println(message);
        } else if (MsgTypeConstant.RESP_MESSAGE_TYPE_IMAGE.equals(msgType)) {
            String mediaId = /*map.get("MediaId")*/"9YLdMsa4U4PHGErwKy4yXXNHaRmFfqEqK_eXzDeZl7JpxZxY2lZ15aOk9b25uwpu";
            // 图片链接（由系统生成）
            String picUrl = map.get("PicUrl");
            /*JSONObject jsonObject = MediaUploadUtil.addMaterialEver(picUrl + ".jpg", MsgTypeConstant.RESP_MESSAGE_TYPE_IMAGE, TokenUtil.getToken(wxProperties).getToken());
            if (jsonObject != null) {
                boolean b = jsonObject.containsKey("media_id");
                if (b) {
                    mediaId = jsonObject.getString("media_id");
                    System.out.println("media_id:" + mediaId);
                }
            }*/
            message = MessageUtil.initImageMessage(mediaId, toUserName, fromUserName);
            System.err.println(message);
        } else if (MsgTypeConstant.REQ_MESSAGE_TYPE_EVENT.equals(msgType)) {
            // 取消与订阅
            String event = map.get("Event");
            TopicMessage topic = new TopicMessage();
            topic.setEvent(event);
            topic.setFromUserName(fromUserName);
            topic.setMsgType("event");
            topic.setCreateTime(System.currentTimeMillis());
            if (MsgTypeConstant.EVENT_TYPE_SUBSCRIBE.equals(event)) {
                message = MessageUtil.topicEventMessageToXml(topic);
                log.info("订阅");
            } else if (MsgTypeConstant.EVENT_TYPE_UNSUBSCRIBE.equals(event)) {
                message = MessageUtil.topicEventMessageToXml(topic);
                log.info("取消订阅");
            }

            // 点击菜单拉取消息时的事件推送
            if (MsgTypeConstant.EVENT_TYPE_CLICK.equals(event)) {
                String eventKey = map.get("EventKey");
                System.err.println("EventKey: " + eventKey);
            }

            // 点击菜单跳转链接时的事件推送
            if (MsgTypeConstant.EVENT_TYPE_VIEW.equals(event)) {
                String eventKey = map.get("EventKey");
                System.err.println("url: " + eventKey);
            }

            // 弹出系统拍照发图的事件推送
            if (MsgTypeConstant.EVENT_TYPE_PIC_SYSPHOTO.equals(event)) {
                String eventKey = map.get("EventKey");
                String sendPicsInfo = map.get("SendPicsInfo");
                String picList = map.get("PicList");
                String count = map.get("Count");
                String picMd5Sum = map.get("PicMd5Sum");

                PicSysPhotoEvent pspe = (PicSysPhotoEvent) new PicSysPhotoEvent()
                        .setCount(count)
                        .setPicList(picList)
                        .setPicMd5Sum(picMd5Sum)
                        .setSendPicsInfo(sendPicsInfo)
                        .setEvent(event)
                        .setEventKey(eventKey)
//                        .setCreateTime(createTime)
                        .setFromUserName(fromUserName);


                System.err.println("pspe: " + pspe);
            }

        }
        // 将回应发送给微信服务器
        System.err.println(message);
        out.print(message);
        out.close();
    }

    @GetMapping("/menu")
    public void menuCreate(HttpServletRequest request, HttpServletResponse response) {
        wxSnackpubService.menuCreate();
    }


    @GetMapping("/menuSelect")
    public void menuSelect() {
        wxSnackpubService.menuSelect();
    }


    @GetMapping("/gxh/menu")
    public void gxhMenuCreate(HttpServletRequest request, HttpServletResponse response) {
        wxSnackpubService.addconditional();
    }


}
