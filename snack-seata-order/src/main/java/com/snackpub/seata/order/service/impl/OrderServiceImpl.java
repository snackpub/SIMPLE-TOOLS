package com.snackpub.seata.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.protobuf.ServiceException;
import com.snackpub.seata.order.entity.Order;
import com.snackpub.seata.order.feign.IBankClient;
import com.snackpub.seata.order.feign.IStorageClient;
import com.snackpub.seata.order.mapper.OrderMapper;
import com.snackpub.seata.order.service.IOrderService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * OrderServiceImpl
 *
 * @author Chill
 */
@Service
@AllArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    private IStorageClient storageClient;
    private IBankClient bankClient;

    @Override
    @GlobalTransactional
    @Transactional(rollbackFor = Exception.class)
    public boolean createOrder(String userId, String commodityCode, Integer count) throws ServiceException {
        int maxCount = 100;
        BigDecimal orderMoney = new BigDecimal(count).multiply(new BigDecimal(5));
        Order order = new Order()
                .setUserId(userId)
                .setCommodityCode(commodityCode)
                .setCount(count)
                .setMoney(orderMoney);
        int cnt1 = baseMapper.insert(order);
        int cnt2 = storageClient.deduct(commodityCode, count);
        int cnt3 = bankClient.deduct(userId, orderMoney,order.getId().longValue());
        if (cnt2 < 0) {
            throw new ServiceException("创建订单失败");
        } else if (count > maxCount) {
            throw new ServiceException("超过订单最大值，创建订单失败");
        } else if (cnt3 < 0) {
            throw new ServiceException("订单扣款失败");
        }
        return cnt1 > 0 && cnt2 > 0 && cnt3 > 0;
    }
}
