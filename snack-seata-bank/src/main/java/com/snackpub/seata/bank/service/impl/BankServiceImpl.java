package com.snackpub.seata.bank.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snackpub.seata.bank.entity.Bank;
import com.snackpub.seata.bank.mapper.BankMapper;
import com.snackpub.seata.bank.service.IBankService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * BankServiceImpl
 *
 * @author snack
 */
@Service
public class BankServiceImpl extends ServiceImpl<BankMapper, Bank> implements IBankService {


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deduct(String userId, BigDecimal money,Long orderId) {
        Bank bank = baseMapper.selectOne(Wrappers.<Bank>query().lambda().eq(Bank::getUserId, userId));
        BigDecimal deductMoney = bank.getAccountBalance().subtract(money);

        if (deductMoney.intValue() < 0) {
            throw new RuntimeException("账户余额不足，扣款失败");
        }
        bank.setAccountBalance(deductMoney);
        bank.setOrderId(orderId);
        return baseMapper.updateById(bank);
    }
}
