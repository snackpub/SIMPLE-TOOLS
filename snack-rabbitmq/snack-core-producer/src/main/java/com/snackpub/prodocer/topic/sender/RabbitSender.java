package com.snackpub.prodocer.topic.sender;

import com.snackpub.common.model.Order;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 消息推送
 *
 * @author snack
 * @date 2020/9/2
 */
@Component
public class RabbitSender {

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * 回调函数: confirm确认
     * 消息发送到交换器Exchange后触发回调
     */
    final ConfirmCallback confirmCallback = (correlationData, ack, cause) -> {
        System.err.println("correlationData: " + correlationData);
        System.err.println("ack: " + ack);
        System.err.println("失败原因: " + cause);
        if (!ack) {
            System.err.println("异常处理....");
        }
    };

    /**
     * 回调函数: return返回
     * 消息从交换器发送到对应队列失败时触发（比如根据发送消息时指定的routingKey找不到队列时会触发）
     */
    final ReturnCallback returnCallback = (message, replyCode, replyText, exchange, routingKey) -> System.err.println("return exchange: " + exchange + ", routingKey: "
            + routingKey + ", replyCode: " + replyCode + ", replyText: " + replyText);

    /**
     * 发送消息方法调用: 构建Message消息
     *
     * @param message    消息
     * @param properties header
     */
    @SneakyThrows
    public void send(Object message, Map<String, Object> properties) {
        MessageHeaders mhs = new MessageHeaders(properties);
        Message msg = MessageBuilder.createMessage(message, mhs);
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
        //id + 时间戳 全局唯一
        CorrelationData correlationData = new CorrelationData("1234567890");
        rabbitTemplate.convertAndSend("exchange-1", "springboot.abc", msg, correlationData);
    }

    /**
     * /发送消息方法调用: 构建自定义对象消息
     *
     * @param order 消息
     */
    @SneakyThrows
    public void sendOrder(Order order) {
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
        //id + 时间戳 全局唯一
        CorrelationData correlationData = new CorrelationData("0987654321");
        rabbitTemplate.convertAndSend("exchange-2", "springboot.def", order, correlationData);
    }

}