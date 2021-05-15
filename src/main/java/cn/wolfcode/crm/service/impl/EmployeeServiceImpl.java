package cn.wolfcode.crm.service.impl;

import cn.wolfcode.crm.domain.Department;
import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.domain.Role;
import cn.wolfcode.crm.mapper.DepartmentMapper;
import cn.wolfcode.crm.mapper.EmployeeMapper;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IEmployeeService;
import cn.wolfcode.crm.util.PageResult;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class EmployeeServiceImpl implements IEmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public void saveOrUpdate(Employee record) {
        if (record.getId() == null) {
            //先对密码加密(用户名为盐,加密2次)
            String password = record.getPassword();
            Md5Hash md5 = new Md5Hash(password, record.getUsername(), 2);
            record.setPassword(md5.toString());
            //保存
            record.setState(true);
            employeeMapper.insert(record);
        } else {
            //更新
            //删除与role的关系
            employeeMapper.deleteRelationWithRole(record.getId());
            employeeMapper.updateByPrimaryKey(record);
        }

        //新增与role的关系
        List<Role> roles = record.getRoles();
        if (roles != null) {
            for (Role role : roles) {
                employeeMapper.insertRelationWithRole(record.getId(), role.getId());
            }
        }
    }

    @Override
    public Employee get(Long id) {
        return employeeMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Employee> list() {
        return employeeMapper.selectAll();
    }

    @Override
    public PageResult query(QueryObject qo) {
        int count = employeeMapper.queryCount(qo);
        if (count == 0) {
            return new PageResult(0, Collections.EMPTY_LIST);
        }
        return new PageResult(count, employeeMapper.queryList(qo));
    }

    @Override
    public boolean validateUsername(String username, Long id) {
        Employee user = employeeMapper.selectByUsername(username);
        return user == null || user.getId() == id;
    }

    @Override
    public void changeState(Long id) {
        employeeMapper.changeState(id);
    }

    @Override
    public Employee selectByUsername(String username) {
        return employeeMapper.selectByUsername(username);
    }

    @Override
    public Workbook export(QueryObject qo) {
        //查询员工数据(有过滤条件)
        List<Employee> list = employeeMapper.selectAllByQuertyObject(qo);

        //创建xlsx工作簿
        Workbook wb = new XSSFWorkbook();

        //日期单元格格式
        CreationHelper createHelper = wb.getCreationHelper();
        CellStyle dateStyle = wb.createCellStyle();
        dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy/MM/dd"));

        //创建表单
        Sheet sheet = wb.createSheet();

        //创建第一行
        Row row = sheet.createRow(0);

        //设置列标题
        row.createCell(0).setCellValue("用户名");
        row.createCell(1).setCellValue("真实姓名");
        row.createCell(2).setCellValue("电话");
        row.createCell(3).setCellValue("邮箱");
        row.createCell(4).setCellValue("部门名称");
        row.createCell(5).setCellValue("入职时间");
        row.createCell(6).setCellValue("状态");
        row.createCell(7).setCellValue("是否管理员");
        //录入数据
        for (int i = 0; i < list.size(); ++i) {
            //从第二行开始录入数据
            row = sheet.createRow(i + 1);
            Employee e = list.get(i);
            //用户名
            row.createCell(0).setCellValue(e.getUsername());
            //真实姓名
            String realname = e.getRealname();
            if (realname != null) {
                row.createCell(1).setCellValue(realname);
            }
            //电话
            String tel = e.getTel();
            if (tel != null) {
                row.createCell(2).setCellValue(tel);
            }
            //邮箱
            String email = e.getEmail();
            if (email != null) {
                row.createCell(3).setCellValue(email);
            }
            //部门名称
            Department dept = e.getDept();
            if (dept != null) {
                row.createCell(4).setCellValue(dept.getName());
            }

            //设置日期单元格格式
            Cell date = row.createCell(5);
            date.setCellStyle(dateStyle);
            //日期
            Date hireDate = e.getHireDate();
            if (hireDate != null) {
                date.setCellValue(hireDate);
            }
            //状态
            Boolean state = e.getState();
            if (state != null) {
                if (state) {
                    row.createCell(6).setCellValue("在职");
                } else {
                    row.createCell(6).setCellValue("离职");
                }
            }
            //是否超管
            Boolean admin = e.getAdmin();
            if (admin) {
                row.createCell(7).setCellValue("是");
            } else {
                row.createCell(7).setCellValue("否");
            }
        }
        return wb;
    }

    @Override
    public void importFile(MultipartFile file) throws IOException {
        Workbook wb = null;
        //判断文件类型
        if (file.getContentType().endsWith(".ms-excel")) {
            //.xls文件
            wb = new HSSFWorkbook(file.getInputStream());
        } else if (file.getContentType().endsWith(".sheet")) {
            //.xlsx文件
            wb = new XSSFWorkbook(file.getInputStream());
        }

        //获取表单
        Sheet sheet = wb.getSheet("sheet1");
        //获取总行数
        int rowNum = sheet.getLastRowNum();
        //迭代获取每一行(第一行不要,因为是列标题)
        for (int i = 1; i <= rowNum; ++i) {
            Row row = sheet.getRow(i);
            //没写用户名,直接跳过这行数据
            Cell cell = row.getCell(0);
            if (cell == null) {
                continue;
            }
            Employee employee = new Employee();
            //用户名
            employee.setUsername(cell.getStringCellValue());
            //真实姓名
            cell = row.getCell(1);
            if (cell != null) {
                employee.setRealname(cell.getStringCellValue());
            }
            //密码
            cell = row.getCell(2);
            if (cell != null) {
                //先对密码加密
                String password = new Md5Hash(cell.getStringCellValue(), employee.getUsername(), 2).toString();
                employee.setRealname(password);
            }
            //电话
            cell = row.getCell(3);
            if (cell != null) {
                employee.setTel(cell.getStringCellValue());
            }
            //邮箱
            cell = row.getCell(4);
            if (cell != null) {
                employee.setEmail(cell.getStringCellValue());
            }
            //部门
            cell = row.getCell(5);
            if (cell != null) {
                Department dept = departmentMapper.selectByName(cell.getStringCellValue());
                employee.setDept(dept);
            }
            //入职时间
            cell = row.getCell(6);
            if (cell != null) {
                employee.setHireDate(cell.getDateCellValue());
            }
            //状态
            cell = row.getCell(7);
            if (cell != null) {
                String state = cell.getStringCellValue();
                if ("离职".equals(state)) {
                    employee.setState(false);
                } else {
                    employee.setState(true);
                }
            }
            //是否超管
            cell = row.getCell(8);
            if (cell != null) {
                String admin = cell.getStringCellValue();
                if ("是".equals(admin)) {
                    employee.setAdmin(true);
                } else {
                    employee.setAdmin(false);
                }
            }
            //保存到数据库中
            employeeMapper.insert(employee);
        }
    }

}
