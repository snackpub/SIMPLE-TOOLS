package com.snackpub.prodocer.header;

import com.snackpub.core.tools.utils.StringUtil;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

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

}
