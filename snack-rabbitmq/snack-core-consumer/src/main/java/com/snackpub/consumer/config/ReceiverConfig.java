package com.snackpub.consumer.config;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author snackpub
 * @date 2020/8/26
 */
@Configuration
public class ReceiverConfig {


    @Bean
    public Queue queue() {
        return new Queue("queue");
    }

    @Bean(name = "message")
    public Queue queueMessage() {
        return new Queue("topic.message");
    }

    @Bean(name = "messages")
    public Queue queueMessages() {
        return new Queue("topic.messages");
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("exchange");
    }

    @Bean
    Binding bindingExchangeMessage(@Qualifier("message") Queue queueMessage, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with("topic.message");
    }

    @Bean
    Binding bindingExchangeMessages(@Qualifier("messages") Queue queueMessage, TopicExchange exchange) {
        //*表示一个词,#表示零个或多个词
        return BindingBuilder.bind(queueMessage).to(exchange).with("topic.#");
    }


    /**
     * 死信队列配置
     */
    @Bean
    public Queue delayQueue() {
        Map<String, Object> properties = new HashMap<>(3);
        // 定义死信时间，单位毫秒
        properties.put("x-message-ttl", 5 * 1000);
        properties.put("x-message-ttl", 5 * 1000);
        // delay-ex 是这些死信要发送到交换器的名字
        properties.put("x-dead-letter-exchange", "delay-ex");
        // 死信在转发时携带的路由键名称
        properties.put("x-dead-routing-key", "order");
        return new Queue("delay-queue", true, false, false, properties);
    }

    @Bean
    public DirectExchange delayExchange() {
        return new DirectExchange("delay-ex");
    }

    /**
     * 保存死信的消息队列
     */
    @Bean
    public Queue orderQueue1() {
        return new Queue("queue-order");
    }

    /**
     * 将队列板顶到交换机的order路由键下面
     */
    @Bean
    Binding bindingDelayExchange() {
        return BindingBuilder.bind(delayQueue()).to(delayExchange()).with("order");
    }


    @Resource
    private ConnectionFactory connectionFactory;

    /**
     * 手工定义消费者进行消息确认
     */
    @Bean
    SimpleMessageListenerContainer orderListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        // 添加监听的队列
        container.addQueues(delayQueue());
        container.setExposeListenerChannel(true);
        container.setMaxConcurrentConsumers(10);
        container.setConcurrentConsumers(1);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setPrefetchCount(1);
        container.setMessageListener(new ChannelAwareMessageListener() {
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                byte[] bytes = message.getBody();
                String data = new String(bytes);
                System.out.println("延迟模式消费者处理消息" + data);
                // 手动ack确认
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
          }
        });
        return container;
    }

}
