package cn.wolfcode.crm.service;

import cn.wolfcode.crm.domain.Permission;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.util.PageResult;

import java.util.Collection;
import java.util.List;

public interface IPermissionService {

    List<Permission> list();

    PageResult query(QueryObject qo);

    /**
     * 加载权限
     */
    void reload();

    /**
     * 查询该角色的所有权限
     * @param roleId 角色的id
     * @return
     */
    List<Permission> selectByRoleId(Long roleId);

    /**
     * 查询该员工的所有权限表达式
     * @param id
     * @return
     */
    List<String> selectResourceByEmployeeId(Long employeeId);
}
