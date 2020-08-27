package com.snackpub.consumer.receiver.topic;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author snackpub
 * @date 2020/8/26
 */
@Component
public class RabbitReceiver2 {

    @RabbitListener(queues = "topic.message")
    public void process1(String message) {
        System.err.println("Topic queue message: " + message);
    }

    /*    @RabbitListener(bindings = @QueueBinding(
                value = @Queue(value = "topic.messages", declare = "true"),
                exchange = @Exchange(value = "exchange", declare = "true", type = "tipic", ignoreDeclarationExceptions = "true"),
                key = "topic.message")
        )*/
    @RabbitListener(queues = "topic.messages")
    public void process2(String message) {
        System.err.println("Topic queue messages: " + message);
    }

}
