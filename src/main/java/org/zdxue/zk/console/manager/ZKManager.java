package org.zdxue.zk.console.manager;

import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.zdxue.zk.console.vo.LeafBean;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author xuezhongde
 */
@Component
public class ZKManager {
    private static final Logger logger = LoggerFactory.getLogger(ZKManager.class);

    @Resource
    private CuratorFramework zkClient;

    public Set<LeafBean> searchNodes(String searchStr, int roleId) throws Exception {
        logger.debug("searchNodes: searchStr={}, roleId={}", searchStr, roleId);
        return search("/", searchStr, new HashSet<>());
    }

    private Set<LeafBean> search(String path, String match, Set<LeafBean> searchNodes) throws Exception {
        List<String> children = zkClient.getChildren().forPath(path);
        if (CollectionUtils.isEmpty(children)) {
            return searchNodes;
        }

        for (String child : children) {
            path = path.equals("/") ? "" : path;
            if (child.contains(match)) {
                searchNodes.add(new LeafBean(path.equals("") ? "/" : path, child, null));
            }

            String childPath = path + "/" + child;
            search(childPath, match, searchNodes);
        }

        return searchNodes;
    }

    public List<String> getNonLeafPaths(String path, int roleId) throws Exception {
        logger.debug("getNonLeafNodes: path={}, roleId={}", path, roleId);

        List<String> children = zkClient.getChildren().forPath(path);
        if (CollectionUtils.isEmpty(children)) {
            return Collections.emptyList();
        }

        List<String> nonLeafPaths = new ArrayList<>(children.size());
        for (String child : children) {
            path = path.equals("/") ? "" : path;
            if (hasChildren(path + "/" + child)) {
                nonLeafPaths.add(child);
            }
        }

        Collections.sort(nonLeafPaths);
        return nonLeafPaths;
    }

    public List<LeafBean> getLeafNodes(String path, int roleId) throws Exception {
        logger.debug("getLeafNodes: path={}, roleId={}", path, roleId);

        List<String> children = zkClient.getChildren().forPath(path);
        if (CollectionUtils.isEmpty(children)) {
            return Collections.emptyList();
        }

        List<LeafBean> leafBeans = new ArrayList<>(children.size());
        for (String child : children) {
            path = path.equals("/") ? "" : path;
            if (!hasChildren(path + "/" + child)) {
                byte[] data = getData(path + "/" + child);
                leafBeans.add(new LeafBean(path, child, data));
            }
        }

        Collections.sort(leafBeans);
        return leafBeans;
    }

    private boolean hasChildren(String path) {
        return !CollectionUtils.isEmpty(getChildren(path, -1));
    }

    public List<String> getChildren(String path, int roleId) {
        logger.debug("getChildren: path={}, roleId={}", path, roleId);
        try {
            List<String> children = zkClient.getChildren().forPath(path);
            Collections.sort(children);
            return children;
        } catch (Exception e) {
            logger.error("list error", e);
        }
        return Collections.emptyList();
    }

    public void createNode(String path, byte[] data) throws Exception {
        zkClient.create().creatingParentsIfNeeded().forPath(path, data);
    }

    public void deleteNodes(List<String> paths) throws Exception {
        for (String path : paths) {
            deleteNode(path);
        }
    }

    public void deleteNode(String path) throws Exception {
        zkClient.delete().forPath(path);
    }

    public void setData(String path, byte[] data) throws Exception {
        zkClient.setData().forPath(path, data);
    }

    public byte[] getData(String path) throws Exception {
        return zkClient.getData().forPath(path);
    }

}
