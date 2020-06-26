package com.snack.seata.storage.controller;

import com.snack.seata.storage.service.IStorageService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * StorageController
 *
 * @author Chill
 */
@RestController
@RequestMapping("storage")
@AllArgsConstructor
public class StorageController {

	private IStorageService storageService;

	/**
	 * 减库存
	 *
	 * @param commodityCode 商品代码
	 * @param count         数量
	 */
	@RequestMapping(path = "/deduct")
	public int deduct(String commodityCode, Integer count) {
		return storageService.deduct(commodityCode, count);
	}

}
