package cn.wolfcode.crm.service;

import cn.wolfcode.crm.domain.SystemDictionaryItem;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.util.PageResult;

import java.util.List;

public interface ISystemDictionaryItemService {

    void delete(Long id);

    void saveOrUpdate(SystemDictionaryItem record);

    SystemDictionaryItem get(Long id);

    List<SystemDictionaryItem> list();

    PageResult query(QueryObject qo);

}
