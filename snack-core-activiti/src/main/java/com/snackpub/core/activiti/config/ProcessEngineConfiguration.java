package com.snackpub.core.activiti.config;

import lombok.extern.slf4j.Slf4j;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.ProcessEngineConfigurationConfigurer;
import org.springframework.stereotype.Component;

/**
 * @author snackpub
 * @date 2020/9/7
 */
@Slf4j
@Component
public class ProcessEngineConfiguration implements ProcessEngineConfigurationConfigurer {

    @Override
    public void configure(SpringProcessEngineConfiguration springProcessEngineConfiguration) {
//        springProcessEngineConfiguration.setActiviti
        springProcessEngineConfiguration.setActivityFontName("宋体");
        springProcessEngineConfiguration.setLabelFontName("宋体");
        springProcessEngineConfiguration.setAnnotationFontName("宋体");
        springProcessEngineConfiguration.setIdGenerator(new IdGen());
        log.info("配置字体：{}", springProcessEngineConfiguration.getActivityFontName());

    }
}
