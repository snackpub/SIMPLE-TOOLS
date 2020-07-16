package com.snackpub.seata.order.controller;

import com.snackpub.core.tools.api.R;
import com.snackpub.seata.order.entity.Order;
import com.snackpub.seata.order.service.IOrderService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * OrderController
 *
 * @author Chill
 */
@RestController
@RequestMapping("order")
@AllArgsConstructor
public class OrderController {

	private IOrderService orderService;

	/**
	 * 创建订单
	 *
	 * @param userId        用户id
	 * @param commodityCode 商品代码
	 * @param count         数量
	 * @return boolean
	 */
	@SneakyThrows
	@RequestMapping("/create")
	public R createOrder(String userId, String commodityCode, Integer count) {
		return R.status(orderService.createOrder(userId, commodityCode, count));
	}

}
