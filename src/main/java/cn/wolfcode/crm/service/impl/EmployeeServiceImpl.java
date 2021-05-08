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
import org.apache.poi.ss.format.CellFormatType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
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
    public Workbook getXlsx() {

        //创建xlsx文件
        Workbook wb = new XSSFWorkbook();

        //日期单元格格式
        CreationHelper createHelper = wb.getCreationHelper();
        CellStyle dateStyle = wb.createCellStyle();
        dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd"));

        //创建工作簿
        Sheet sheet = wb.createSheet("sheet1");
        //查询员工数据
        List<Employee> list = employeeMapper.selectAll();
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
            row.createCell(0).setCellValue(e.getUsername());
            row.createCell(1).setCellValue(e.getRealname());
            row.createCell(2).setCellValue(e.getTel());
            row.createCell(3).setCellValue(e.getEmail());
            if (e.getDept() != null) {
                row.createCell(4).setCellValue(e.getDept().getName());
            }

            //日期单元格
            Cell date = row.createCell(5);
            date.setCellStyle(dateStyle);
            date.setCellValue(e.getHireDate());
            //状态
            if (e.getState()) {
                row.createCell(6).setCellValue("在职");
            } else {
                row.createCell(6).setCellValue("离职");
            }
            //是否超管
            if (e.getAdmin()) {
                row.createCell(7).setCellValue("是");
            } else {
                row.createCell(7).setCellValue("否");
            }
        }
        return wb;
    }

    @Override
    public void importXlsx(MultipartFile file) throws IOException {
        Workbook wb = null;
        //判断文件类型
        if (file.getContentType().endsWith(".ms-excel")) {
            //.xls文件
            wb = new HSSFWorkbook(file.getInputStream());
        } else if (file.getContentType().endsWith(".sheet")) {
            //.xlsx文件
            wb = new XSSFWorkbook(file.getInputStream());
        }

        //获取工作簿
        Sheet sheet = wb.getSheet("sheet1");
        //获取总行数
        int rowNum = sheet.getLastRowNum();
        //迭代获取每一行(第一行不要,因为是列标题)
        for (int i = 1; i <= rowNum; ++i) {
            Row row = sheet.getRow(i);
            //没写用户名直接跳过
            Cell cell = row.getCell(0);
            if (cell == null) {
                continue;
            }

            Employee employee = new Employee();
            //用户名
            employee.setUsername(cell.getStringCellValue());
            //真实姓名
            employee.setRealname(row.getCell(1).getStringCellValue());
            //密码
            employee.setPassword("假密码");
            //电话
            employee.setTel(row.getCell(2).getStringCellValue());
            //邮箱
            employee.setEmail(row.getCell(3).getStringCellValue());
            //部门
            Department dept = departmentMapper.selectByName(row.getCell(4).getStringCellValue());
            employee.setDept(dept);
            //入职时间
            employee.setHireDate(row.getCell(5).getDateCellValue());
            //状态
            if ("在职".equals(row.getCell(6).getStringCellValue())) {
                employee.setState(true);
            } else if ("离职".equals(row.getCell(6).getStringCellValue())) {
                employee.setState(false);
            }
            //是否超管
            String admin = row.getCell(7).getStringCellValue();
            if ("是".equals(row.getCell(7).getStringCellValue())) {
                employee.setAdmin(true);
            } else if ("否".equals(row.getCell(7).getStringCellValue())) {
                employee.setAdmin(false);
            }
            //保存到数据库中
            employeeMapper.insert(employee);
        }
    }

}
