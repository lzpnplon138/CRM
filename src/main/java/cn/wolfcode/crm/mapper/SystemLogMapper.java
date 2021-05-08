package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.domain.SystemLog;
import cn.wolfcode.crm.query.QueryObject;

import java.util.List;

public interface SystemLogMapper {
    int insert(SystemLog record);

    int deleteByPrimaryKey(Long id);

    SystemLog selectByPrimaryKey(Long id);

    List<SystemLog> selectAll();

    int queryCount(QueryObject qo);

    List<SystemLog> queryList(QueryObject qo);

}