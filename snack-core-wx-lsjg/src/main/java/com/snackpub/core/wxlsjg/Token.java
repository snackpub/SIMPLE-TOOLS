package com.snackpub.core.wxlsjg;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * token info
 *
 * @author snackpub
 * @date 2020/9/13
 */
@Data
@Accessors(chain = true)
public class Token {


    /**
     * 令牌
     */
    private String token;
    /**
     * 过期秒数
     */
    private int expire;
}
