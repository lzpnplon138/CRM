package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.query.EmployeeQueryObject;
import cn.wolfcode.crm.service.IEmployeeService;
import cn.wolfcode.crm.util.JSONResult;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private IEmployeeService employeeService;


    //员工页面
    @RequiresPermissions(value = {"employee:view", "员工界面"}, logical = Logical.OR)
    @RequestMapping("/view")
    public String view() {
        return "employee";
    }

    //员工分页数据
    @RequiresPermissions(value = {"employee:query", "员工列表"}, logical = Logical.OR)
    @ResponseBody
    @RequestMapping("/query")
    public Object query(EmployeeQueryObject qo) {
        return employeeService.query(qo);
    }

    //编辑时验证用户名
    @ResponseBody
    @RequestMapping("/validateUsername")
    public Object validateUsername(String username, Long id) {
        return employeeService.validateUsername(username, id);
    }

    // 新增/更新
    @RequiresPermissions(value = {"employee:saveOrUpdate", "员工保存/更新"}, logical = Logical.OR)
    @ResponseBody
    @RequestMapping("/saveOrUpdate")
    public Object saveOrUpdate(Employee employee) {
        JSONResult result = new JSONResult();
        try {
            employeeService.saveOrUpdate(employee);
        } catch (Exception e) {
            e.printStackTrace();
            result.mark("保存失败");
        }
        return result;
    }

    // 更改员工状态(在职/离职)
    @ResponseBody
    @RequestMapping("/changeState")
    public Object changeState(Long id) {
        JSONResult result = new JSONResult();
        try {
            employeeService.changeState(id);
        } catch (Exception e) {
            e.printStackTrace();
            result.mark("操作失败");
        }
        return result;
    }

    //导出
    @RequestMapping("/export")
    public void exportXlsx(HttpServletResponse response,EmployeeQueryObject qo) throws IOException {
        //设置下载的响应头
        response.setHeader("Content-disposition", "attachment;filename=employee.xlsx");
        //获取xlsx文件
        Workbook wb = employeeService.export(qo);
        //输出到浏览器
        wb.write(response.getOutputStream());
    }

    //导入
    @RequestMapping("/importFile")
    @ResponseBody
    public Object importFile(MultipartFile file)   {
        JSONResult result = new JSONResult();
        try {
            employeeService.importFile(file);
        } catch (Exception e) {
            e.printStackTrace();
            result.mark(e.getMessage());
        }
        return result;
    }

}
