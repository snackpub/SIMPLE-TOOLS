package com.snackpub.seata.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.google.protobuf.ServiceException;
import com.snackpub.seata.order.entity.Order;

/**
 * IOrderService
 *
 * @author Chill
 */
public interface IOrderService extends IService<Order> {

    /**
     * 创建订单
     *
     * @param userId        用户id
     * @param commodityCode 商品代码
     * @param count         数量
     * @return boolean
     */
    boolean createOrder(String userId, String commodityCode, Integer count) throws ServiceException;

    /**
     * 创建订单保存到mq
     *
     * @param userId        用户id
     * @param commodityCode 商品代码
     * @param count         数量
     * @return boolean
     */
    void createOrderSendMq(String userId, String commodityCode, Integer count) throws ServiceException;


}
