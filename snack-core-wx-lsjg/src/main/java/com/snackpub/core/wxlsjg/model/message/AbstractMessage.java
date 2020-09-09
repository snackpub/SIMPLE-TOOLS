package com.snackpub.core.wxlsjg.model.message;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 微信消息抽象
 *
 * @author snackpub
 * @date 2020/9/8
 */
@Data
@Accessors(chain = true)
public abstract class AbstractMessage implements Serializable {

    /**
     * 开发者微信号
     */
    protected String ToUserName;
    /**
     * 发送方帐号（一个OpenID）
     */
    protected String FromUserName;
    /**
     * 消息创建时间 （整型）
     */
    protected Long CreateTime;
    /**
     * 消息类型，文本为text
     */
    protected String MsgType;
    /**
     * 消息id，64位整型
     */
    protected String MsgId;

}
