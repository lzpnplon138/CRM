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
     * 编辑时,验证部门的名称
     * 新增操作时不能与已有部门名称重复,
     * 更新操作可以和原来的部门名称相同,但不能与其他已有部门名称相同
     * @param name 部门名称
     * @param id 部门id
     * @return
     */
    boolean validateName(String name,Long id);

    /**
     * 改变部门状态(启用/禁用)
     * @param id 部门id
     */
    void changeState(Long id);

}
