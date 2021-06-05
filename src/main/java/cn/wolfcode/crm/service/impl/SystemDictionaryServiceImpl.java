package cn.wolfcode.crm.service.impl;

import cn.wolfcode.crm.domain.SystemDictionary;
import cn.wolfcode.crm.mapper.SystemDictionaryMapper;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.ISystemDictionaryService;
import cn.wolfcode.crm.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SystemDictionaryServiceImpl implements ISystemDictionaryService {
    @Autowired
    private SystemDictionaryMapper systemDictionaryMapper;

    @Override
    public void delete(Long id) {
        systemDictionaryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void saveOrUpdate(SystemDictionary record) {
        if (record.getId() == null) {
            systemDictionaryMapper.insert(record);
        } else {
            //更新
            systemDictionaryMapper.updateByPrimaryKey(record);
        }
    }

    @Override
    public SystemDictionary get(Long id) {
        return systemDictionaryMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SystemDictionary> list() {
        return systemDictionaryMapper.selectAll();
    }

    @Override
    public PageResult query(QueryObject qo) {
        int count = systemDictionaryMapper.queryCount(qo);
        if (count == 0) {
            return new PageResult(0, Collections.EMPTY_LIST);
        }
        return new PageResult(count, systemDictionaryMapper.queryList(qo));
    }

}
