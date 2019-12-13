package org.zdxue.zk.console.controller;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.zdxue.zk.console.manager.ZKManager;
import org.zdxue.zk.console.model.UserRole;
import org.zdxue.zk.console.springboot.autoconfigure.ZKConsoleProperties;
import org.zdxue.zk.console.vo.LeafBean;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author xuezhongde
 */
@Controller
public class HomeController extends AbstractController {

    @Resource
    private HttpServletRequest request;

    @Resource
    private ZKConsoleProperties zkConsoleProperties;

    @Resource
    private ZKManager zkManager;

    @RequestMapping(value = "/home", method = {RequestMethod.GET, RequestMethod.POST})
    public String home(ModelMap model) throws Exception {
        if (request.getMethod().equalsIgnoreCase(HttpMethod.GET.toString())) {
            return doGet(model);
        } else if (request.getMethod().equalsIgnoreCase(HttpMethod.POST.toString())) {
            return doPost(model);
        }

        return "error";
    }

    private String doGet(ModelMap model) throws Exception {
        String zkPath = request.getParameter("zkPath");
        String navigate = request.getParameter("navigate");
        String currentPath = "/", parentPath = "/", displayPath = "/";
        int roleId = getSessionUserRoleId();

        if (StringUtils.isEmpty(zkPath) || zkPath.equals("/")) {
            zkPath = "/";
        } else {
            currentPath = zkPath + "/";
            displayPath = zkPath;
            parentPath = zkPath.substring(0, zkPath.lastIndexOf("/"));
            if (parentPath.equals("")) {
                parentPath = "/";
            }
        }

        List<String> nodeLst = zkManager.getNonLeafPaths(zkPath, roleId);
        List<LeafBean> leafLst = zkManager.getLeafNodes(zkPath, roleId);

        model.put("currentPath", currentPath);
        model.put("displayPath", displayPath);
        model.put("parentPath", parentPath);
        model.put("breadCrumbLst", displayPath.split("/"));
        model.put("nodeLst", nodeLst);
        model.put("leafLst", leafLst);
        model.put("scmRepo", zkConsoleProperties.getScmRepo());
        model.put("scmRepoPath", zkConsoleProperties.getScmRepoPath());
        model.put("navigate", navigate);

        return "home";
    }

    private String doPost(ModelMap model) throws Exception {
        String action = request.getParameter("action");
        String currentPath = request.getParameter("currentPath");
        String displayPath = request.getParameter("displayPath");
        String newProperty = request.getParameter("newProperty");
        String newValue = request.getParameter("newValue");
        String newNode = request.getParameter("newNode");
        String[] nodeChkGroup = request.getParameterValues("nodeChkGroup");
        String[] propChkGroup = request.getParameterValues("propChkGroup");

        String searchStr = request.getParameter("searchStr").trim();
        UserRole role = UserRole.of(getSessionUserRoleId());

        switch (action) {
            case "Save Node":
                if (!newNode.equals("") && !currentPath.equals("") && role.equals(UserRole.ADMIN)) {
                    zkManager.createNode(currentPath + newNode, "".getBytes());
                    request.getSession().setAttribute("flashMsg", "Node created!");
                    //dao.insertHistory((String) request.getSession().getAttribute("authName"), request.getRemoteAddr(), "Creating node: " + currentPath + newNode);
                }
                break;
            case "Save Property":
                if (!newProperty.equals("") && !currentPath.equals("") && role.equals(UserRole.ADMIN)) {
                    zkManager.createNode(currentPath + newProperty, newValue.getBytes());
                    request.getSession().setAttribute("flashMsg", "Property Saved!");
                    //dao.insertHistory((String) request.getSession().getAttribute("authName"), request.getRemoteAddr(), "Saving Property: " + currentPath + "," + newProperty + "=" + newValue);
                }
                break;
            case "Update Property":
                if (!newProperty.equals("") && !currentPath.equals("") && role.equals(UserRole.ADMIN)) {
                    zkManager.setData(currentPath + newProperty, newValue.getBytes());
                    request.getSession().setAttribute("flashMsg", "Property Updated!");
                    //dao.insertHistory((String) request.getSession().getAttribute("authName"), request.getRemoteAddr(), "Updating Property: " + currentPath + "," + newProperty + "=" + newValue);
                }
                break;
            case "Search":
                Set<LeafBean> searchResult = zkManager.searchNodes(searchStr, role.getRoleId());
                model.put("searchResult", searchResult);
                return "search";
            case "Delete":
                if (role.equals(UserRole.ADMIN)) {
                    if (propChkGroup != null) {
                        for (String prop : propChkGroup) {
                            List<String> delPropLst = Arrays.asList(prop);
                            zkManager.deleteNodes(delPropLst);
                            request.getSession().setAttribute("flashMsg", "Delete Completed!");
                            //dao.insertHistory((String) request.getSession().getAttribute("authName"), request.getRemoteAddr(), "Deleting Property: " + delPropLst.toString());
                        }
                    }
                    if (nodeChkGroup != null) {
                        for (String node : nodeChkGroup) {
                            List<String> delNodeLst = Arrays.asList(node);
                            zkManager.deleteNodes(delNodeLst);
                            request.getSession().setAttribute("flashMsg", "Delete Completed!");
                            //dao.insertHistory((String) request.getSession().getAttribute("authName"), request.getRemoteAddr(), "Deleting Nodes: " + delNodeLst.toString());
                        }
                    }
                }
                break;
            default:
                //No-op
        }

        return "redirect:/home?zkPath=" + displayPath;
    }

}