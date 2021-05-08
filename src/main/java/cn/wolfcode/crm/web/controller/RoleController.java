package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.domain.Role;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IRoleService;
import cn.wolfcode.crm.util.JSONResult;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private IRoleService roleService;

    @RequiresPermissions(value = {"role:view","角色视图"},logical = Logical.OR)
    @RequestMapping("/view")
    public String view() {
        return "role";
    }

    @ResponseBody
    @RequestMapping("/list")
    public Object list() {
        return roleService.list();
    }

    @RequiresPermissions(value = {"role:query","角色列表"},logical = Logical.OR)
    @ResponseBody
    @RequestMapping("/query")
    public Object query(QueryObject qo) {
        return roleService.query(qo);
    }

    @RequiresPermissions(value = {"role:saveOrUpdate","角色保存/更新"},logical = Logical.OR)
    @ResponseBody
    @RequestMapping("/saveOrUpdate")
    public Object saveOrUpdate(Role role) {
        JSONResult result = new JSONResult();
        try {
            roleService.saveOrUpdate(role);
        } catch (Exception e) {
            e.printStackTrace();
            result.mark("保存失败");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/selectByEmployeeId")
    public Object selectByEmployeeId(Long employeeId) {
        return roleService.selectByEmployeeId(employeeId);
    }

}
