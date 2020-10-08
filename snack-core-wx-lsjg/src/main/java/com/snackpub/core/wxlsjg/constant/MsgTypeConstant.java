package com.snackpub.core.wxlsjg.constant;

/**
 * 消息类型常量
 *
 * @author snackpub
 * @date 2020/9/9
 */
public interface MsgTypeConstant {

    /**
     * 返回消息类型：文本
     */
    String RESP_MESSAGE_TYPE_TEXT = "text";

    /**
     * 返回消息类型：音乐
     */
    String RESP_MESSAGE_TYPE_MUSIC = "music";

    /**
     * 返回消息类型：图文
     */
    String RESP_MESSAGE_TYPE_NEWS = "news";

    /**
     * 返回消息类型：图片
     */
    String RESP_MESSAGE_TYPE_IMAGE = "image";

    /**
     * 返回消息类型：语音
     */
    String RESP_MESSAGE_TYPE_Voice = "voice";

    /**
     * 请求消息类型：推送
     */
    String REQ_MESSAGE_TYPE_EVENT = "event";

    /**
     * 事件类型：subscribe(订阅)
     */
    String EVENT_TYPE_SUBSCRIBE = "subscribe";

    /**
     * 事件类型：unsubscribe(取消订阅)
     */
    String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

    /**
     * 事件类型：CLICK(自定义菜单点击事件)
     */
    String EVENT_TYPE_CLICK = "CLICK";

    /**
     * 事件类型：VIEW(自定义菜单 URl 视图)
     */
    String EVENT_TYPE_VIEW = "VIEW";

    /**
     * 事件类型: 弹出拍照或者相册发图的事件推送
     */
    String EVENT_TYPE_PIC_PHOTO_OR_ALBUM = "pic_photo_or_album";

    /**
     * 弹出系统拍照发图的事件推送
     */
    String EVENT_TYPE_PIC_SYSPHOTO = "pic_sysphoto";
}

