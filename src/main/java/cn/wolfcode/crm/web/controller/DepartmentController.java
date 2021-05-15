package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.domain.Department;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IDepartmentService;
import cn.wolfcode.crm.util.JSONResult;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    private IDepartmentService departmentService;

    //返回部门视图
    @RequiresPermissions(value = {"department:view","部门视图"},logical = Logical.OR)
    @RequestMapping("/view")
    public String view() {
        return "department";
    }

    //返回所有部门对象的集合
    @ResponseBody
    @RequestMapping("/list")
    public Object list() {
        return departmentService.list();
    }

    //部门分页数据
    @RequiresPermissions(value = {"department:query","部门列表"},logical = Logical.OR)
    @ResponseBody
    @RequestMapping("/query")
    public Object query(QueryObject qo) {
        return departmentService.query(qo);
    }

    // 新增/更新
    @RequiresPermissions(value = {"department:saveOrUpdate","部门保存/更新"},logical = Logical.OR)
    @ResponseBody
    @RequestMapping("/saveOrUpdate")
    public Object saveOrUpdate(Department department) {
        JSONResult result = new JSONResult();
        try {
            departmentService.saveOrUpdate(department);
        } catch (Exception e) {
            e.printStackTrace();
            result.mark("保存失败");
        }
        return result;
    }

    // 启用/禁用
    @ResponseBody
    @RequestMapping("/changeState")
    public Object changeState(Long id) {
        JSONResult result = new JSONResult();
        try {
            departmentService.changeState(id);
        } catch (Exception e) {
            e.printStackTrace();
            result.mark("操作失败");
        }
        return result;
    }

    //编辑时验证部门名称
    @ResponseBody
    @RequestMapping("/validateName")
    public Object validateName(String name, Long id) {
        return departmentService.validateName(name, id);
    }
}
