package org.zdxue.zk.console.service.impl;

import org.springframework.stereotype.Service;
import org.zdxue.zk.console.model.User;
import org.zdxue.zk.console.model.UserRole;
import org.zdxue.zk.console.service.LoginService;

/**
 * @author xuezhongde
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Override
    public User login(String username, String password) {
        //TODO
        if ("zk-admin".equals(username) && "123456".equals(password)) {
            User user = new User();
            user.setUsername(username);
            user.setRole(UserRole.ADMIN);
            return user;
        } else if ("zk-user".equals(username) && "123456".equals(password)) {
            User user = new User();
            user.setUsername(username);
            user.setRole(UserRole.ORDINARY);
            return user;
        }

        return null;
    }

}
