package cn.wolfcode.crm.service.impl;

import cn.wolfcode.crm.domain.Department;
import cn.wolfcode.crm.domain.Department;
import cn.wolfcode.crm.mapper.DepartmentMapper;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IDepartmentService;
import cn.wolfcode.crm.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class DepartmentServiceImpl implements IDepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public void saveOrUpdate(Department record) {
        if (record.getId() == null) {
            //新增
            record.setState(true);
            departmentMapper.insert(record);
        } else {
            //更新
            departmentMapper.updateByPrimaryKey(record);
        }
    }

    @Override
    public Department get(Long id) {
        return departmentMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Department> list() {
        return departmentMapper.selectAll();
    }

    @Override
    public PageResult query(QueryObject qo) {
        int count = departmentMapper.queryCount(qo);
        if (count == 0) {
            return new PageResult(0, Collections.EMPTY_LIST);
        }
        return new PageResult(count, departmentMapper.queryList(qo));
    }

    @Override
    public boolean validateName(String name, Long id) {
        Department dept = departmentMapper.selectByName(name);
        return dept == null || dept.getId() == id;
    }

    @Override
    public void changeState(Long id) {
        departmentMapper.changeState(id);
    }

}
