package com.galaxy.flowable.engine.handler;

import com.galaxy.flowable.engine.ActProcess;
import com.galaxy.flowable.engine.ServiceFactory;
import org.flowable.engine.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

/**
 * 流程定义
 *
 * @author liuxz
 * @date 2019/08/30
 */
@Component
public class ProcessHandler extends ServiceFactory implements ActProcess {

    protected static Logger logger = LoggerFactory.getLogger(ProcessHandler.class);

    /**
     * 解决问题：https://blog.csdn.net/weixin_43607664/article/details/88664777
     */
    public static final String BPMN_FILE_SUFFIX = ".bpmn";

    @Override
    public DeploymentBuilder createDeployment() {
        return repositoryService.createDeployment();
    }

    @Override
    public DeploymentQuery createDeploymentQuery() {
        return repositoryService.createDeploymentQuery();
    }

    @Override
    public ProcessDefinitionQuery createProcessDefinitionQuery() {
        return repositoryService.createProcessDefinitionQuery();
    }

    @Override
    public Deployment deploy(String bpmnFileUrl) {
        Deployment deploy = createDeployment().addClasspathResource(bpmnFileUrl).deploy();
        return deploy;
    }

    @Override
    public Deployment deploy(String url, String pngUrl) {

        Deployment deploy = createDeployment().addClasspathResource(url).addClasspathResource(pngUrl).deploy();
        return deploy;
    }

    @Override
    public Deployment deploy(String name, String tenantId, String category, ZipInputStream zipInputStream) {
        return createDeployment().addZipInputStream(zipInputStream)
                .name(name).category(category).tenantId(tenantId).deploy();
    }

    @Override
    public Deployment deployBpmnAndDrl(String url, String drlUrl) {
        Deployment deploy = createDeployment().addClasspathResource(url).addClasspathResource(drlUrl).deploy();
        return deploy;
    }

    @Override
    public Deployment deploy(String url, String name, String category) {
        Deployment deploy = createDeployment().addClasspathResource(url).name(name).category(category).deploy();
        return deploy;
    }

    @Override
    public Deployment deploy(String url, String pngUrl, String name, String category) {
        Deployment deploy = createDeployment().addClasspathResource(url).addClasspathResource(pngUrl)
                .name(name).category(category).deploy();
        return deploy;
    }

    @Override
    public boolean exist(String processDefinitionKey) {
        ProcessDefinitionQuery processDefinitionQuery
                = createProcessDefinitionQuery().processDefinitionKey(processDefinitionKey);
        long count = processDefinitionQuery.count();
        return count > 0 ? true : false;
    }

    @Override
    public Deployment deploy(String name, String tenantId, String category, InputStream in) {
        return createDeployment().addInputStream(name + BPMN_FILE_SUFFIX, in)
                .name(name)
                .tenantId(tenantId)
                .category(category)
                .deploy();

    }

    @Override
    public ProcessDefinition queryByProcessDefinitionKey(String processDefinitionKey) {
        ProcessDefinition processDefinition
                = createProcessDefinitionQuery()
                .processDefinitionKey(processDefinitionKey)
                .active().singleResult();
        return processDefinition;
    }

    @Override
    public Deployment deployName(String deploymentName) {
        List<Deployment> list = repositoryService
                .createDeploymentQuery()
                .deploymentName(deploymentName).list();
        Assert.notNull(list, "list must not be null");
        return list.get(0);
    }

    @Override
    public void addCandidateStarterUser(String processDefinitionKey, String userId) {
        repositoryService.addCandidateStarterUser(processDefinitionKey, userId);
    }

}
