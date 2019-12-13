package org.zdxue.zk.console.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author xuezhongde
 */
@Controller
public class MonitorController extends AbstractController {

    @Resource
    private HttpServletRequest request;

    @RequestMapping(value = "/monitor", method = {RequestMethod.GET, RequestMethod.POST})
    public String monitor(ModelMap model) throws Exception {
        //TODO
        model.put("stats", "TODO");
        return "monitor";
    }

}