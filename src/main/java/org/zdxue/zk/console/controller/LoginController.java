package org.zdxue.zk.console.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.zdxue.zk.console.model.User;
import org.zdxue.zk.console.service.LoginService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author xuezhongde
 */
@Controller
public class LoginController extends AbstractController {

    @Resource
    private HttpServletRequest request;

    @Resource
    private LoginService loginService;

    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public String login(ModelMap model) throws Exception {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String action = request.getParameter("action");

        User user = loginService.login(username, password);
        if (user != null) {
            request.getSession().setAttribute("user", user);
            return "redirect:/home";
        } else {
            request.getSession().setAttribute("flashMsg", "login failure!");
            return "login";
        }
    }

    @RequestMapping(value = "/logout", method = {RequestMethod.GET, RequestMethod.POST})
    public String logout() throws Exception {
        invalidateSession();
        return "login";
    }

}