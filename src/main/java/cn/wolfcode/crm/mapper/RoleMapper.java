package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.domain.Role;
import cn.wolfcode.crm.query.QueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int insert(Role record);

    int updateByPrimaryKey(Role record);

    int deleteByPrimaryKey(Long id);

    Role selectByPrimaryKey(Long id);

    List<Role> selectAll();

    int queryCount(QueryObject qo);

    List<Employee> queryList(QueryObject qo);

    /**
     * 删除与permission的关系
     * @param roleId 角色id
     */
    void deleteRelationWithPermission(Long roleId);

    /**
     * 新增与permission的关系
     * @param roleId 角色id
     * @param permissionId 权限id
     */
    void insertRelationWithPermission(@Param("roleId") Long roleId,@Param("permissionId") Long permissionId);

    /**
     * 查询该员工的所有角色的id
     * @param employeeId 员工id
     * @return
     */
    List<Long> selectByEmployeeId(Long employeeId);

    /**
     * 删除与menu的关系
     * @param roleId 角色id
     */
    void deleteRelationWithMenu(Long roleId);

    /**
     * 新增与menu的关系
     * @param roleId 角色id
     * @param menuId 菜单id
     */
    void insertRelationWithMenu(@Param("roleId") Long roleId,@Param("menuId") Long menuId);

    /**
     * 查询该员工的所有角色的name
     * @param employeeId 员工id
     * @return
     */
    List<String> selectNameByEmployeeId(Long employeeId);
}