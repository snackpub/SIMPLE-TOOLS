package com.snackpub.core.wxlsjg.model.event;

import com.snackpub.core.wxlsjg.model.message.AbstractMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 弹出系统拍照发图的事件推送
 *
 * @author snackpub
 * @date 2020/9/11
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class PicSysPhotoEvent extends AbstractEvent {

    /**
     * 发送的图片信息
     */
    private String SendPicsInfo;

    /**
     * 发送的图片数量
     */
    private String Count;

    /**
     * 图片列表
     */
    private String PicList;

    /**
     * 图片的MD5值，开发者若需要，可用于验证接收到图片
     */
    private String PicMd5Sum;

}
