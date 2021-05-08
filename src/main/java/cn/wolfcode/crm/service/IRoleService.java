package cn.wolfcode.crm.service;

import cn.wolfcode.crm.domain.Role;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.util.PageResult;

import java.util.Collection;
import java.util.List;

public interface IRoleService {
    void saveOrUpdate(Role record);

    Role get(Long id);

    List<Role> list();

    PageResult query(QueryObject qo);

    /**
     * 查询该员工的所有角色的id
     * @param employeeId
     * @return
     */
    List<Long> selectByEmployeeId(Long employeeId);

    /**
     * 查询该员工的所有角色的name
     * @param employeeId
     * @return
     */
    List<String> selectNameByEmployeeId(Long employeeId);
}
