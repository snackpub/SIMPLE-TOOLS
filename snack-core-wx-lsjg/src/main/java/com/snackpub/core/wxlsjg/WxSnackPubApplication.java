package com.snackpub.core.wxlsjg;

import com.snackpub.core.launch.SnackPubApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * 启动类
 *
 * @author qhj
 */
@SpringCloudApplication
public class WxSnackPubApplication {

    public static void main(String[] args) {
        SnackPubApplication.run("wxSnackPub-server", WxSnackPubApplication.class, args);
    }

}
