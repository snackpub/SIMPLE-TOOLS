package com.snackpub;

import com.snackpub.core.cloud.feign.EnableRisesinFeign;
import com.snackpub.core.launch.SnackPubApplication;
import com.snackpub.core.launch.constant.AppConstant;
import com.snackpub.transaction.annotation.SeataCloudApplication;

/**
 * 启动类
 *
 * @author snack
 */
@SeataCloudApplication
@EnableRisesinFeign(AppConstant.BASE_PACKAGES)
public class SeataBankApplication {

    public static void main(String[] args) {
        SnackPubApplication.run("snack-seata-bank", SeataBankApplication.class, args);
    }

}
