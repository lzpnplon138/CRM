package cn.wolfcode.crm.service;

import cn.wolfcode.crm.domain.Department;
import cn.wolfcode.crm.domain.SystemLog;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.util.PageResult;

import java.util.List;

public interface ISystemLogService {
    void save(SystemLog record);

    SystemLog get(Long id);

    List<SystemLog> list();

    PageResult query(QueryObject qo);


}
