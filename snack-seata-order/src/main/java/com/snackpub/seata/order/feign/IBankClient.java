package com.snackpub.seata.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * @author snackpub
 * @date 2020/7/15
 */
@FeignClient(name = "snack-seata-bank", fallback = BankClientFallback.class)
public interface IBankClient {

    /**
     * 扣款
     *
     * @param userId 用户id
     * @param money  金额
     */
    @RequestMapping(path = "/bank/deduct")
    int deduct(@RequestParam("userId") String userId, @RequestParam("money") BigDecimal money, @RequestParam("orderId") Long orderId);


}
