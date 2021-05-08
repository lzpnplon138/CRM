package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.domain.SystemLog;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.ISystemLogService;
import cn.wolfcode.crm.util.JSONResult;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/systemLog")
public class SystemLogController {
    @Autowired
    private ISystemLogService systemLogService;

    @RequestMapping("/view")
    public String view() {
        return "systemLog";
    }

    @ResponseBody
    @RequestMapping("/query")
    public Object query(QueryObject qo) {
        return systemLogService.query(qo);
    }

}
