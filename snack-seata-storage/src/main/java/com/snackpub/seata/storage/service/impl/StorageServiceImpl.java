package com.snackpub.seata.storage.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snackpub.seata.storage.service.IStorageService;
import com.snackpub.seata.storage.entity.Storage;
import com.snackpub.seata.storage.mapper.StorageMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @RabbitListener(queues = {"queue.topic"})
    @Override
    public void deduct2(String orderJson) {
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
    }


}
