package org.zdxue.zk.console.service;

import org.zdxue.zk.console.model.User;

/**
 * @author xuezhongde
 */
public interface LoginService {

    public User login(String username, String password);

}
