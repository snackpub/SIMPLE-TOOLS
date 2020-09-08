package com.snackpub.core.activiti;

import com.snackpub.core.activiti.service.ActivitiService;
import com.snackpub.test.BladeBaseTest;
import com.snackpub.test.BladeBootTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@BladeBootTest
public class SnackCoreActivitiApplicationTests extends BladeBaseTest {

    @Autowired
    private ActivitiService activitiService;

    @Test
    public void contextLoads() {
        activitiService.startActiviti();
    }

}
