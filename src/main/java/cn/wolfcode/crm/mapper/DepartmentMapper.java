package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.Department;
import cn.wolfcode.crm.domain.Department;
import cn.wolfcode.crm.query.QueryObject;

import java.util.List;

public interface DepartmentMapper {
    int insert(Department record);

    int updateByPrimaryKey(Department record);

    int deleteByPrimaryKey(Long id);

    Department selectByPrimaryKey(Long id);

    List<Department> selectAll();

    int queryCount(QueryObject qo);

    List<Department> queryList(QueryObject qo);

    /**
     * 通过部门名称查询对应部门对象
     * @param name 部门名称
     * @return
     */
    Department selectByName(String name);

    /**
     * 改变部门状态(启用/禁用)
     * @param id 部门id
     */
    void changeState(Long id);
}