package com.snackpub.prodocer;

import com.snackpub.core.launch.SnackPubApplication;
import org.springframework.cloud.client.SpringCloudApplication;

@SpringCloudApplication
public class ProducerApplication {

    public static void main(String[] args) {
        SnackPubApplication.run("snack-mq-producer", ProducerApplication.class, args);
    }

}
