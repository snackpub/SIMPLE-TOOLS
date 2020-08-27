package com.snackpub.consumer.receiver.direct;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author snackpub
 * @date 2020/8/26
 */
@Component
public class HelloReceiver {

    @RabbitListener(queues = "queue") // 监听指定的队列
    public void receiverMsg(String str) {
        System.err.println("queue message: " + str);
    }

}
