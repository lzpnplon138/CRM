package cn.wolfcode.crm.service;

import cn.wolfcode.crm.domain.SystemLog;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.util.PageResult;

public interface ISystemLogService {

    void save(SystemLog record);


    PageResult query(QueryObject qo);


}
