package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.domain.SystemDictionary;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.ISystemDictionaryService;
import cn.wolfcode.crm.util.JSONResult;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/systemDictionary")
public class SystemDictionaryController {
    @Autowired
    private ISystemDictionaryService systemDictionaryService;

    //返回字典视图
    @RequiresPermissions(value = {"systemDictionary:view","字典视图"},logical = Logical.OR)
    @RequestMapping("/view")
    public String view() {
        return "systemDictionary";
    }

    //返回所有字典对象的集合
    @ResponseBody
    @RequestMapping("/list")
    public Object list() {
        return systemDictionaryService.list();
    }

    //字典分页数据
    @RequiresPermissions(value = {"systemDictionary:query","字典列表"},logical = Logical.OR)
    @ResponseBody
    @RequestMapping("/query")
    public Object query(QueryObject qo) {
        return systemDictionaryService.query(qo);
    }

    // 新增/更新
    @RequiresPermissions(value = {"systemDictionary:saveOrUpdate","字典保存/更新"},logical = Logical.OR)
    @ResponseBody
    @RequestMapping("/saveOrUpdate")
    public Object saveOrUpdate(SystemDictionary systemDictionary) {
        JSONResult result = new JSONResult();
        try {
            systemDictionaryService.saveOrUpdate(systemDictionary);
        } catch (Exception e) {
            e.printStackTrace();
            result.mark("保存失败");
        }
        return result;
    }

    // 删除
    @RequiresPermissions(value = {"systemDictionary:delete","字典删除"},logical = Logical.OR)
    @ResponseBody
    @RequestMapping("/delete")
    public Object delete(Long id) {
        JSONResult result = new JSONResult();
        try {
            systemDictionaryService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            result.mark("保存失败");
        }
        return result;
    }
}
