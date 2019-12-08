package org.zdxue.zk.console.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

/**
 * @author xuezhongde
 */
@Controller
public class HomeController {

    @Resource
    private HttpServletRequest request;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(ModelMap model) {

        //TODO

        model.put("currentPath", "");
        model.put("parentPath", "");
        model.put("displayPath", "");
        model.put("scmRepo", "");
        model.put("scmRepoPath", "");
        model.put("nodeLst", Collections.emptyList());
        model.put("leafLst", Collections.emptyList());

        return "home";
    }

}