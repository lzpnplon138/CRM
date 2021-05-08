package cn.wolfcode.crm.service;

import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.util.PageResult;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IEmployeeService {
    void saveOrUpdate(Employee record);

    Employee get(Long id);

    List<Employee> list();

    PageResult query(QueryObject qo);

    /**
     * 验证用户名是否存在
     * @param username
     * @return
     */
    boolean validateUsername(String username,Long id);

    /**
     * 改变员工状态
     * @param id
     */
    void changeState(Long id);

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    Employee selectByUsername(String username);

    /**
     * 从数据库中导出数据到Xlsx
     * @return
     */
    Workbook getXlsx();

    /**
     * 导入Xlsx文件的数据到数据库中
     * @param wb
     */
    void importXlsx(MultipartFile file) throws IOException;
}
