package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IPermissionService;
import cn.wolfcode.crm.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private IPermissionService permissionService;

    @RequestMapping("/view")
    public String view() {
        return "permission";
    }

    @ResponseBody
    @RequestMapping("/list")
    public Object list() {
        return permissionService.list();
    }

    @ResponseBody
    @RequestMapping("/query")
    public Object query(QueryObject qo) {
        return permissionService.query(qo);
    }

    @ResponseBody
    @RequestMapping("/selectByRoleId")
    public Object selectByRoleId(Long roleId) {
        return permissionService.selectByRoleId(roleId);
    }

    @ResponseBody
    @RequestMapping("/reload")
    public Object reload() {
        JSONResult result = new JSONResult();
        try {
            permissionService.reload();
        } catch (Exception e) {
            e.printStackTrace();
            result.mark("加载失败");
        }
        return result;
    }

}
