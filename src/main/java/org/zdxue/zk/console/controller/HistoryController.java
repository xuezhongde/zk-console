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
public class HistoryController extends AbstractController {

    @Resource
    private HttpServletRequest request;

    @RequestMapping(value = "/history", method = {RequestMethod.GET, RequestMethod.POST})
    public String history(ModelMap model) throws Exception {
        //TODO
        model.put("historyLst", Collections.emptyList());
        return "history";
    }

}