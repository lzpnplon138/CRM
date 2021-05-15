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
     * 编辑时,验证员工的名称
     * 新增操作时不能与已有员工名称重复,
     * 更新操作可以和原来的员工名称相同,但不能与其他已有员工名称相同
     * @param username  员工名称
     * @param id  员工id
     * @return
     */
    boolean validateUsername(String username,Long id);

    /**
     * 改变员工状态(在职/离职)
     * @param id 员工id
     */
    void changeState(Long id);

    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return
     */
    Employee selectByUsername(String username);

    /**
     * 从数据库中导出数据给用户
     * @return
     * @param qo
     */
    Workbook export(QueryObject qo);

    /**
     * 导入上传的xls/xlsx文件的数据到数据库中
     * @param file 上传的文件
     */
    void importFile(MultipartFile file) throws IOException;
}
