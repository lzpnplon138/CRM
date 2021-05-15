package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.domain.Permission;
import cn.wolfcode.crm.query.QueryObject;

import java.util.List;

public interface PermissionMapper {
    int insert(Permission record);

    int deleteByPrimaryKey(Long id);

    List<Permission> selectAll();

    int queryCount(QueryObject qo);

    List<Employee> queryList(QueryObject qo);

    /**
     * 查询所有权限表达式
     * @return
     */
    List<String> selectAllResource();

    /**
     * 查询该角色的所有权限
     * @param roleId 角色的id
     * @return
     */
    List<Permission> selectByRoleId(Long roleId);


    /**
     * 查询该员工的所有权限表达式
     * @param employeeId  员工id
     * @return
     */
    List<String> selectResourceByEmployeeId(Long employeeId);
}