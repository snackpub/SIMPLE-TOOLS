package com.snackpub.core.activiti.service.impl;

import com.snackpub.core.activiti.service.ActivitiService;
import lombok.SneakyThrows;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author snackpub
 * @date 2020/9/7
 */
@Service
public class ActivitiServiceImpl implements ActivitiService {

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TaskService taskService;
    @Resource
    private ProcessEngine processEngine;

    @SneakyThrows
    @Override
    public void startActiviti() {
        File file = new File("E:\\projects\\snack-seata\\snack-core-activiti\\src\\main\\resources\\processes\\test.bpmn");
        InputStream is = new FileInputStream(file);
        RepositoryService repositoryService = processEngine.getRepositoryService();
        DeploymentBuilder deployment = repositoryService.createDeployment();
        // deployment.addInputStream("test.bpmn", is);
        deployment.addClasspathResource("processes/test.bpmn");
        deployment.deploy();

        Map<String, Object> map = new HashMap<>(1);
        map.put("apply", "kefu");

        // 启动流程
        ExecutionEntity executionEntity = (ExecutionEntity) runtimeService.startProcessInstanceByKey("myProcess_1", map);
        String processId = executionEntity.getId();
        System.err.println(processId);

        List<Task> task = taskService.createTaskQuery().taskAssignee("kefu").list();
        task.forEach(s -> {
            System.err.println(s.getName());
            System.err.println(s.getAssignee());
            System.err.println(s.getCreateTime());
        });

    }
}
