package com.snackpub.core.activiti;

import com.snackpub.core.launch.SnackPubApplication;
import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 *
 * @author qhj
 */
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class SanckActivitiApplication {

    public static void main(String[] args) {
        SnackPubApplication.run("snack-activiti", SanckActivitiApplication.class, args);
    }

}
