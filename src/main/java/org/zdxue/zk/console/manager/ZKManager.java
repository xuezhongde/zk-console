package org.zdxue.zk.console.manager;

import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.zdxue.zk.console.vo.LeafBean;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author xuezhongde
 */
@Component
public class ZKManager {
    private static final Logger logger = LoggerFactory.getLogger(ZKManager.class);

    @Resource
    private CuratorFramework zkClient;

    //TODO
    public List<String> getChildren(String path, int roleId) {
        logger.debug("getChildren: path={}, roleId={}", path, roleId);
        try {
            return zkClient.getChildren().forPath(path);
        } catch (Exception e) {
            logger.error("list error", e);
        }
        return Collections.emptyList();
    }

    //TODO
    public List<LeafBean> getLeaves(String path, int roleId) {
        logger.debug("getLeaves: path={}, roleId={}", path, roleId);
        try {
            List<LeafBean> leaves = new ArrayList<>(3);
            leaves.add(new LeafBean("111", "xxx", "hello".getBytes()));
            leaves.add(new LeafBean("222", "yyy", "hi".getBytes()));
            leaves.add(new LeafBean("333", "zzz", "yeap".getBytes()));
            return leaves;
        } catch (Exception e) {
            logger.error("list error", e);
        }
        return Collections.emptyList();
    }

    public void createNode(String path, byte[] data) throws Exception {
        //TODO 匿名内部类不能修改外部变量
        zkClient.create().creatingParentsIfNeeded().forPath(path, data);
    }

    public void setData(String path, byte[] data) throws Exception {
        //TODO 匿名内部类不能修改外部变量
        zkClient.setData().forPath(path, data);
    }

}
