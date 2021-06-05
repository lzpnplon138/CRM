package cn.wolfcode.crm.service.impl;

import cn.wolfcode.crm.domain.SystemDictionaryItem;
import cn.wolfcode.crm.mapper.SystemDictionaryItemMapper;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.ISystemDictionaryItemService;
import cn.wolfcode.crm.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SystemDictionaryItemServiceImpl implements ISystemDictionaryItemService {
    @Autowired
    private SystemDictionaryItemMapper systemDictionaryItemMapper;

    @Override
    public void delete(Long id) {
        systemDictionaryItemMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void saveOrUpdate(SystemDictionaryItem record) {
        if (record.getId() == null) {
            systemDictionaryItemMapper.insert(record);
        } else {
            //更新
            systemDictionaryItemMapper.updateByPrimaryKey(record);
        }
    }

    @Override
    public SystemDictionaryItem get(Long id) {
        return systemDictionaryItemMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SystemDictionaryItem> list() {
        return systemDictionaryItemMapper.selectAll();
    }

    @Override
    public PageResult query(QueryObject qo) {
        int count = systemDictionaryItemMapper.queryCount(qo);
        if (count == 0) {
            return new PageResult(0, Collections.EMPTY_LIST);
        }
        return new PageResult(count, systemDictionaryItemMapper.queryList(qo));
    }



}
