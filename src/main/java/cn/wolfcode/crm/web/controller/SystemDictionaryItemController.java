package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.domain.SystemDictionaryItem;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.query.SystemDictionaryItemQueryObject;
import cn.wolfcode.crm.service.ISystemDictionaryItemService;
import cn.wolfcode.crm.util.JSONResult;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/systemDictionaryItem")
public class SystemDictionaryItemController {
    @Autowired
    private ISystemDictionaryItemService systemDictionaryItemService;
    

    //返回所有字典明细对象的集合
    @ResponseBody
    @RequestMapping("/list")
    public Object list() {
        return systemDictionaryItemService.list();
    }

    //字典明细分页数据
    @RequiresPermissions(value = {"systemDictionaryItem:query","字典明细列表"},logical = Logical.OR)
    @ResponseBody
    @RequestMapping("/query")
    public Object query(SystemDictionaryItemQueryObject qo) {
        return systemDictionaryItemService.query(qo);
    }

    // 新增/更新
    @RequiresPermissions(value = {"systemDictionaryItem:saveOrUpdate","字典明细保存/更新"},logical = Logical.OR)
    @ResponseBody
    @RequestMapping("/saveOrUpdate")
    public Object saveOrUpdate(SystemDictionaryItem systemDictionaryItem) {
        JSONResult result = new JSONResult();
        try {
            systemDictionaryItemService.saveOrUpdate(systemDictionaryItem);
        } catch (Exception e) {
            e.printStackTrace();
            result.mark("保存失败");
        }
        return result;
    }

    // 删除
    @RequiresPermissions(value = {"systemDictionaryItem:delete","字典明细删除"},logical = Logical.OR)
    @ResponseBody
    @RequestMapping("/delete")
    public Object delete(Long id) {
        JSONResult result = new JSONResult();
        try {
            systemDictionaryItemService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            result.mark("保存失败");
        }
        return result;
    }
}
