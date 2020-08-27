package com.snackpub.prodocer.direct;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author snackpub
 * @date 2020/8/26
 */
@Component
public class HelloSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send() {
        amqpTemplate.convertAndSend("queue", "hello,rabbit~~");
    }


    public void topicMessageSend() {
        amqpTemplate.convertAndSend("exchange", "topic.message", "hello,rabbit~~11");
    }

    public void topicMessagesSend() {
        amqpTemplate.convertAndSend("exchange", "topic.messages", "hello,rabbit~~22");
    }
}
