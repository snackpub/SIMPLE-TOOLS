package com.snackpub.prodocer;

import com.snack.common.model.Order;
import com.snackpub.prodocer.direct.HelloSender;
import com.snackpub.prodocer.topic.sender.RabbitSender;
import com.snackpub.test.BladeBaseTest;
import com.snackpub.test.BladeBootTest;
import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@BladeBootTest
public class RabbitmqSpringcloudProducerApplicationTests extends BladeBaseTest {

    @Autowired
    private RabbitSender rabbitSender;

    @Autowired
    private HelloSender helloSender;

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    @Test
    public void testSender1() throws Exception {
        Map<String, Object> properties = new HashMap<>();
        properties.put("number", "12345");
        properties.put("send_time", simpleDateFormat.format(new Date()));
        rabbitSender.send("Hello RabbitMQ For Spring Boot!", properties);
    }

    @Test
    public void testSender2() throws Exception {
        Order order = new Order("001", "第一个订单");
        rabbitSender.sendOrder(order);
    }

    @SneakyThrows
    @Test
    public void testSender3() {
        helloSender.send();
        System.err.println("推送成功！");
    }

    @SneakyThrows
    @Test
    public void testSender4() {
        helloSender.topicMessageSend();
        System.err.println("推送成功！");
    }

    @SneakyThrows
    @Test
    public void testSender5() {
        helloSender.topicMessagesSend();
        System.err.println("推送成功！");
    }

    @SneakyThrows
    @Test
    public void testSender6() {
        helloSender.send2();
        System.err.println("推送成功！");
    }


}