package com.snackpub.seata.bank.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.snackpub.seata.bank.entity.Bank;

import java.math.BigDecimal;

/**
 * IBankService
 *
 * @author Chill
 */
public interface IBankService extends IService<Bank> {

    /**
     * 扣款
     *
     * @param userId 用户id
     * @param money  金额
     * @return boolean
     */
    int deduct(String userId, BigDecimal money, Long orderId);

}
