package com.snackpub.seata.storage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.snackpub.seata.storage.entity.Storage;

/**
 * IStorageService
 *
 * @author Chill
 */
public interface IStorageService extends IService<Storage> {

	/**
	 * 减库存
	 *
	 * @param commodityCode 商品代码
	 * @param count         数量
	 * @return boolean
	 */
	int deduct(String commodityCode, int count);



	/**
	 * 减库存
	 *
	 * @param jsonOrder 商品代码
	 */
	void deduct2(String jsonOrder);

}
