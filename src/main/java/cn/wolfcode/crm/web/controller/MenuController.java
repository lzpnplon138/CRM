package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private IMenuService menuService;

    //返回所有层级菜单
    @ResponseBody
    @RequestMapping("/selectRootMenus")
    public Object selectRootMenus() {
        return menuService.selectRootMenus();
    }

    //返回该角色的菜单
    @ResponseBody
    @RequestMapping("/selectByRoleId")
    public Object selectByRoleId(Long roleId) {
        return menuService.selectByRoleId(roleId);
    }

    //返回该员工的菜单
    @ResponseBody
    @RequestMapping("/selectEmployeeMenu")
    public Object selectEmployeeMenu() {
        return menuService.selectEmployeeMenu();
    }

}
