package com.snackpub.consumer.receiver.header;

import com.rabbitmq.client.Channel;
import lombok.SneakyThrows;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * header exchange receiver sample
 *
 * @author snackpub
 * @date 2020/9/1
 */
@Component
public class HeaderReceiver {

    @SneakyThrows
    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "queue-header", declare = "true"),
            exchange = @Exchange(value = "exchange-header", type = ExchangeTypes.HEADERS, declare = "true", ignoreDeclarationExceptions = "false")))
    @RabbitHandler
    public void onMessage(Message message, Channel channel, @Headers Map<String, Object> headers) {
        System.err.println("--------------------------------------");
        System.err.println("消费端Payload: " + message.getPayload());
        Long id = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        channel.basicAck(id, false);

    }

    @SneakyThrows
    @RabbitListener(
            bindings = @QueueBinding(value = @Queue(value = "queue-nack", declare = "true"),
                    exchange = @Exchange(value = "exchange-nack", type = ExchangeTypes.TOPIC, declare = "true", ignoreDeclarationExceptions = "false")
                    , key = {"nack.*"}
            )
    )
    @RabbitHandler
    public void onMessage2(Message message, Channel channel, @Headers Map<String, Object> headers) {
        System.err.println("--------------------------------------");
        System.err.println("消费端Payload: " + message.getPayload());
        Long id = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        System.err.println("DeliveryTag: " + id);

        // 休眠以便查看
        System.err.println("----------------休眠以便查看------------");
//        Thread.sleep(2000);


        // 手动签收
        Integer count = (Integer) headers.get("n");
        System.err.println("iiiiiiiiiiii============" + count);
        if (count == 1) {
            // 第一个参数deliveryTag来表示接收的顺序，如果deliveryTag填个3，那么<=3的消息都将被拒收； 第三个参数为是否重返队列
            //channel.basicNack(id, false, true);
            System.out.println("count1");
            channel.basicReject(count, false);
        } else {
            System.out.println("ack success");
            channel.basicAck(id, false);
        }


    }

}
