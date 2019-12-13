package org.zdxue.zk.console.springboot.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author xuezhongde
 */
@ConfigurationProperties(prefix = ZKConsoleProperties.DEFAULT_PREFIX)
public class ZKConsoleProperties {
    public static final String DEFAULT_PREFIX = "zk.console";

    private String zkServer;
    private String scmRepo;
    private String scmRepoPath;
    private String dataPath;

    public String getZkServer() {
        return zkServer;
    }

    public void setZkServer(String zkServer) {
        this.zkServer = zkServer;
    }

    public String getScmRepo() {
        return scmRepo;
    }

    public void setScmRepo(String scmRepo) {
        this.scmRepo = scmRepo;
    }

    public String getScmRepoPath() {
        return scmRepoPath;
    }

    public void setScmRepoPath(String scmRepoPath) {
        this.scmRepoPath = scmRepoPath;
    }

    public String getDataPath() {
        return dataPath;
    }

    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }

}
