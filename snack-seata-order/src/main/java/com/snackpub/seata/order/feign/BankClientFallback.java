package com.snackpub.seata.order.feign;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * StorageClientFallback
 *
 * @author Chill
 */
@Component
public class BankClientFallback implements IBankClient {


    @Override
    public int deduct(String userId, BigDecimal money, Long orderId) {
        return -1;
    }

}
