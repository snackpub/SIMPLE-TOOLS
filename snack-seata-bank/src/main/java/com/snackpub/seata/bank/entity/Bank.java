package com.snackpub.seata.bank.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * Bank
 *
 * @author snackpub
 * @date 2020/7/15
 */
@Data
@Accessors(chain = true)
@TableName("tb_bank")
public class Bank {

    private static final long serialVersionUID = 1L;

    private int id;
    private String userId;
    private String bankAccount;
    private BigDecimal accountBalance;
    private Long orderId;
}
