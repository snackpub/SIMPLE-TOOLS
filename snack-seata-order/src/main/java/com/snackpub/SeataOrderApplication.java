package com.snackpub;

import com.snackpub.core.launch.SnackPubApplication;
import com.snackpub.core.launch.constant.AppConstant;
import com.snackpub.transaction.annotation.SeataCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 启动类
 *
 * @author snack
 */
@SeataCloudApplication
@EnableFeignClients(AppConstant.BASE_PACKAGES)
public class SeataOrderApplication {

    public static void main(String[] args) {
        SnackPubApplication.run("snack-seata-order", SeataOrderApplication.class, args);
    }

}
