package org.zdxue.zk.console.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zdxue.zk.console.servlet.listener.ApplicationStartupListener;

/**
 * @author xuezhongde
 */
@Configuration
public class Beans {

    @Bean
    public ApplicationStartupListener applicationStartupListener() {
        return new ApplicationStartupListener();
    }

}