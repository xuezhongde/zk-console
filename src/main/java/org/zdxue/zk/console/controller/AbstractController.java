package org.zdxue.zk.console.controller;

import org.zdxue.zk.console.model.User;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author xuezhongde
 */
public abstract class AbstractController {

    @Resource
    private HttpServletRequest request;

    protected User getSessionUser() {
        return (User) request.getSession().getAttribute("user");
    }

    protected int getSessionUserRoleId() {
        User user = getSessionUser();
        return user == null ? -1 : user.getRole().getRoleId();
    }

    protected void invalidateSession() {
        request.getSession().removeAttribute("user");
        request.getSession().invalidate();
    }

}
