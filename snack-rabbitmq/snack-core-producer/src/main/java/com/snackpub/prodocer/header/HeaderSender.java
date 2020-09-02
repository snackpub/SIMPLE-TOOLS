package com.snackpub.prodocer.header;

import com.rabbitmq.client.Return;
import com.rabbitmq.client.ReturnCallback;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * header exchange sample
 *
 * @author snackpub
 * @date 2020/9/1
 */
@Component
public class HeaderSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    public void execute(Object message, Map<String, Object> headers) {
        MessageHeaders messageHeaders = new MessageHeaders(headers);
        Message<Object> message1 = MessageBuilder.createMessage(message, messageHeaders);

        CorrelationData id = new CorrelationData("123456");

        rabbitTemplate.convertAndSend("exchange-header", "", message1, id);
    }

    final RabbitTemplate.ConfirmCallback confirmCallback = (correlationData, ack, cause) -> {
        System.err.println("correlationData: " + correlationData);
        System.err.println("ack: " + ack);
        System.err.println("失败原因: " + cause);
        if (!ack) {
            System.err.println("异常处理....");
        }
    };

    final RabbitTemplate.ReturnCallback returnCallback = (message, replyCode, replyText, exchange, routingKey) ->
            System.err.println("return exchange: " + exchange + ", routingKey: "
                    + routingKey + ", replyCode: " + replyCode + ", replyText: " + replyText);


    /**
     * basicNack 重回队列测试
     */
    public void execute2(Object message, Map<String, Object> headers) {
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
        String shortMessage;
        for (int i = 0; i < 5; i++) {
            shortMessage = message + " -> 第 " + i + "条,ack重回队列";
            headers.put("n", i);
            MessageHeaders messageHeaders = new MessageHeaders(headers);
            Message<Object> message1 = MessageBuilder.createMessage(shortMessage, messageHeaders);

            CorrelationData id = new CorrelationData("123456");
            rabbitTemplate.convertAndSend("exchange-nack", "nack.def", message1, id);
        }
    }


}
