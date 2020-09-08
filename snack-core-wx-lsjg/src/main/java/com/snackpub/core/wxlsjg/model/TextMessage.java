package com.snackpub.core.wxlsjg.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 微信文本消息
 *
 * @author snackpub
 * @date 2020/9/8
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TextMessage extends AbstractMessage implements Serializable {

    /**
     * 文本消息内容
     */
    protected String Content;

}
