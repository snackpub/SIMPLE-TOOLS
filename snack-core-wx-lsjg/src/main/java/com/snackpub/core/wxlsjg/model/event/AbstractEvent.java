package com.snackpub.core.wxlsjg.model.event;

import com.snackpub.core.wxlsjg.model.message.AbstractMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 事件类型消息监听抽象类
 *
 * @author snackpub
 * @date 2020/9/11
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractEvent extends AbstractMessage implements Serializable {

    /**
     * 事件类型
     */
    private String Event;

    /**
     * 事件KEY值，由开发者在创建菜单时设定
     */
    private String EventKey;


}
