package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.query.QueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeMapper {

    int insert(Employee record);

    int updateByPrimaryKey(Employee record);

    Employee selectByPrimaryKey(Long id);

    List<Employee> selectAll();

    int queryCount(QueryObject qo);

    List<Employee> queryList(QueryObject qo);

    Employee selectByUsername(String username);

    void changeState(Long id);

    /**
     * 删除与role的关系
     * @param employeeId
     */
    void deleteRelationWithRole(Long employeeId);

    /**
     * 新增与role的关系
     * @param employeeId
     * @param roleId
     */
    void insertRelationWithRole(@Param("employeeId") Long employeeId, @Param("roleId") Long roleId);

}