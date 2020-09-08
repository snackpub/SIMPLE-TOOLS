package com.snackpub.core.wxlsjg.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 微信图片消息
 *
 * @author snackpub
 * @date 2020/9/8
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ImageMessage extends AbstractMessage implements Serializable {

    /**
     * 图片链接（由系统生成）
     */
    private String PicUrl;

    /**
     * 图片消息媒体id，可以调用获取临时素材接口拉取数据
     */
    private String MediaId;


}
