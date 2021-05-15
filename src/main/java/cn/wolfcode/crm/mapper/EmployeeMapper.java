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

    /**
     * 通过用户名查询对应员工对象
     *
     * @param username 用户名
     * @return
     */
    Employee selectByUsername(String username);

    /**
     * 改变员工状态(在职/离职)
     *
     * @param id 员工id
     */
    void changeState(Long id);

    /**
     * 删除与role的关系
     *
     * @param employeeId 员工id
     */
    void deleteRelationWithRole(Long employeeId);

    /**
     * 新增与role的关系
     *
     * @param employeeId 员工id
     * @param roleId     角色id
     */
    void insertRelationWithRole(@Param("employeeId") Long employeeId,
                                @Param("roleId") Long roleId);

    /**
     * 查询所有符合条件的员工对象
     * @param qo
     * @return
     */
    List<Employee> selectAllByQuertyObject(QueryObject qo);
}