package com.snackpub.consumer;


import com.snackpub.core.launch.SnackPubApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * 启动类
 *
 * @author qhj
 * @date 2020/8/25
 */
@SpringCloudApplication
public class ConsumerApplication {

    public static void main(String[] args) {
        SnackPubApplication.run("snack-mq-consumer", ConsumerApplication.class, args);
    }

}
