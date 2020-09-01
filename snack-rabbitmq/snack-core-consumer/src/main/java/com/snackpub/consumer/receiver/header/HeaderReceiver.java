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

}
