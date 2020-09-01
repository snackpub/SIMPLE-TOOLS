package com.snackpub.seata.storage.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rabbitmq.client.Channel;
import com.snackpub.seata.storage.service.IStorageService;
import com.snackpub.seata.storage.entity.Storage;
import com.snackpub.seata.storage.mapper.StorageMapper;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Map;

/**
 * StorageServiceImpl
 *
 * @author Chill
 */
@Service
public class StorageServiceImpl extends ServiceImpl<StorageMapper, Storage> implements IStorageService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deduct(String commodityCode, int count) {
        Storage storage = baseMapper.selectOne(Wrappers.<Storage>query().lambda().eq(Storage::getCommodityCode, commodityCode));
        if (storage.getCount() < count) {
            throw new RuntimeException("超过库存数，扣除失败！");
        }
        storage.setCount(storage.getCount() - count);
        return baseMapper.updateById(storage);
    }

    //    @RabbitListener(queues = {"queue.topic"})
    @RabbitListener(bindings = {@QueueBinding(
            value = @Queue(value = "queue.topic", declare = "true"),
            exchange = @Exchange(value = "exchange-3", type = "topic", declare = "true"),
            key = {"order.*"}
    )})
    @SneakyThrows
    public void deduct2(@Payload String orderJson, Channel channel, @Headers Map<String, Object> header) {
        JSONObject order = JSON.parseObject(orderJson);
        Integer count = order.getInteger("count");
        String commodityCode = order.getString("commodityCode");
        Storage storage = baseMapper.selectOne(Wrappers.<Storage>query().lambda().eq(Storage::getCommodityCode, commodityCode));
        if (storage.getCount() < count) {
            throw new RuntimeException("超过库存数，扣除失败！");
        }
        storage.setCount(storage.getCount() - count);
        int i = baseMapper.updateById(storage);
        if (i > 0) {
            System.err.println("deduct storage success");
        }
        Long id = (Long) header.get(AmqpHeaders.DELIVERY_TAG);
        channel.basicAck(id, false);
    }


}
