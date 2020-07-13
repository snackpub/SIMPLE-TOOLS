package com.snack.zipkin;

import com.snackpub.core.launch.SnackPubApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * 启动类
 *
 * @author snack
 */
@SpringCloudApplication
public class ZipkinApplication {

    public static void main(String[] args) {
        SnackPubApplication.run("snack-zipkin", ZipkinApplication.class, args);
    }

}
