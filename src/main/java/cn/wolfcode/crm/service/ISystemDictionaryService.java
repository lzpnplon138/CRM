package cn.wolfcode.crm.service;

import cn.wolfcode.crm.domain.SystemDictionary;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.util.PageResult;

import java.util.List;

public interface ISystemDictionaryService {

    void delete(Long id);

    void saveOrUpdate(SystemDictionary record);

    SystemDictionary get(Long id);

    List<SystemDictionary> list();

    PageResult query(QueryObject qo);

}
