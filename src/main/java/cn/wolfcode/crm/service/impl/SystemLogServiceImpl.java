package cn.wolfcode.crm.service.impl;

import cn.wolfcode.crm.domain.SystemLog;
import cn.wolfcode.crm.mapper.SystemLogMapper;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.ISystemLogService;
import cn.wolfcode.crm.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SystemLogServiceImpl implements ISystemLogService {
    @Autowired
    private SystemLogMapper systemLogMapper;

    @Override
    public void save(SystemLog record) {
        systemLogMapper.insert(record);
    }

    @Override
    public SystemLog get(Long id) {
        return systemLogMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SystemLog> list() {
        return systemLogMapper.selectAll();
    }

    @Override
    public PageResult query(QueryObject qo) {
        int count = systemLogMapper.queryCount(qo);
        if (count == 0) {
            return new PageResult(0, Collections.EMPTY_LIST);
        }
        return new PageResult(count, systemLogMapper.queryList(qo));
    }

}
