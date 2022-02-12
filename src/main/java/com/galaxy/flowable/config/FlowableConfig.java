package com.galaxy.flowable.config;

import lombok.SneakyThrows;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * @author liuxz
 * @date Created in 26-08-2019
 * @description activiti配置类
 */
@Configuration
public class FlowableConfig
    implements EngineConfigurationConfigurer<SpringProcessEngineConfiguration> {

  @SneakyThrows
  @Override
  public void configure(SpringProcessEngineConfiguration configuration) {
    // 自动部署已有的流程文件
    Resource[] resources =
        new PathMatchingResourcePatternResolver()
            .getResources(ResourceLoader.CLASSPATH_URL_PREFIX + "processes/*.bpmn20.xml");
    // 是否自动创建流程引擎表
    configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
    configuration.setDeploymentResources(resources);
    // configuration.setDbIdentityUsed(false);
    // configuration.setAsyncExecutorActivate(false);
    // 流程历史等级
    // configuration.setHistoryLevel(HistoryLevel.FULL);
    // 流程图字体
    configuration.setActivityFontName("宋体");
    configuration.setAnnotationFontName("宋体");
    configuration.setLabelFontName("宋体");
  }
}
