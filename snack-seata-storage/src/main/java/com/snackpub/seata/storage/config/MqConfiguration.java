package com.snackpub.seata.storage.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * declare queue(s)
 *
 * @author snackpub
 * @date 2020/8/27
 */
@Configuration
public class MqConfiguration {

    @Bean
    public Queue queue() {
        return new Queue("queue.topic");
    }

}
