package com.snackpub.seata.storage.controller;

import com.snackpub.seata.storage.service.IStorageService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * StorageController
 *
 * @author Chill
 */
@RestController
@RequestMapping("storage")
@AllArgsConstructor
public class StorageController {

    private IStorageService storageService;

    /**
     * 减库存
     *
     * @param commodityCode 商品代码
     * @param count         数量
     */
    @RequestMapping(path = "/deduct")
    public int deduct(String commodityCode, Integer count) {
        return storageService.deduct(commodityCode, count);
    }


    @RequestMapping(value = "/deduct2")
    public void deduct2(Message message) {
        byte[] body = message.getBody();
        MessageProperties messageProperties = message.getMessageProperties();
    }


}
