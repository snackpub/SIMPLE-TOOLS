package com.snackpub.core.wxlsjg.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 关注/取消关注事件
 *
 * @author snackpub
 * @date 2020/9/8
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class TopicMessage extends AbstractMessage implements Serializable {

    /**
     * 事件类型，subscribe(订阅)、unsubscribe(取消订阅)
     */
    private String Event;

}
