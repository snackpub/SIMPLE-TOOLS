package com.snackpub.seata.bank.controller;

import com.snackpub.seata.bank.service.IBankService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * BankController
 *
 * @author snack
 */
@RestController
@RequestMapping("bank")
@AllArgsConstructor
public class BankController {

    private IBankService bankService;

    /**
     * 扣款
     *
     * @param userId 用户id
     * @param money  金额
     */
    @RequestMapping(path = "/deduct")
    public int deduct(String userId, BigDecimal money,Long orderId) {
        return bankService.deduct(userId, money,orderId);
    }

}
