package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.domain.Menu;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IMenuService;
import cn.wolfcode.crm.util.JSONResult;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private IMenuService menuService;

    @RequestMapping("/view")
    public String view() {
        return "menu";
    }

    @ResponseBody
    @RequestMapping("/list")
    public Object list() {
        return menuService.list();
    }


    @ResponseBody
    @RequestMapping("/saveOrUpdate")
    public Object saveOrUpdate(Menu menu) {
        JSONResult result = new JSONResult();
        try {
            menuService.saveOrUpdate(menu);
        } catch (Exception e) {
            e.printStackTrace();
            result.mark("保存失败");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/selectRootMenus")
    public Object selectRootMenus() {
        return menuService.selectRootMenus();
    }
    @ResponseBody
    @RequestMapping("/selectByRoleId")
    public Object selectByRoleId(Long roleId) {
        return menuService.selectByRoleId(roleId);
    }

    @ResponseBody
    @RequestMapping("/selectEmployeeMenu")
    public Object selectEmployeeMenu() {
        return menuService.selectEmployeeMenu();
    }

}
