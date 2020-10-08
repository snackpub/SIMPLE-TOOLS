package com.snackpub.core.wxlsjg.model.message;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author snackpub
 * @date 2020/9/11
 */
@Data
@AllArgsConstructor
public class Image {

    /**
     * 图片消息媒体id，可以调用获取临时素材接口拉取数据
     */
    private String MediaId;
}
