package cn.wolfcode.crm.service;

import cn.wolfcode.crm.domain.Department;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.util.PageResult;

import java.util.List;

public interface IDepartmentService {
    void saveOrUpdate(Department record);

    Department get(Long id);

    List<Department> list();

    PageResult query(QueryObject qo);

    /**
     * 验证部门名称是否存在
     * @param name
     * @return
     */
    boolean validateName(String name,Long id);

    /**
     * 改变员工状态
     * @param id
     */
    void changeState(Long id);

    /**
     * 通过部门名称查询部门对象
     * @param deptName 部门名称
     * @return
     */
    Department selectByName(String deptName);
}
