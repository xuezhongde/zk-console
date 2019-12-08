package org.zdxue.zk.console.springboot.autoconfigure;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author xuezhongde
 */
@Configuration
@EnableConfigurationProperties(ZKConsoleProperties.class)
public class ZKConsoleAutoConfiguration {

    @Resource
    private ZKConsoleProperties zkConsoleProperties;

    @Bean
    public CuratorFramework zkClient() {
        String connectString = zkConsoleProperties.getZkServer();
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework zkClient = CuratorFrameworkFactory.newClient(connectString, retryPolicy);
        zkClient.start();
        return zkClient;
    }

}
