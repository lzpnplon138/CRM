package cn.wolfcode.crm.service.impl;

import cn.wolfcode.crm.domain.Menu;
import cn.wolfcode.crm.domain.Permission;
import cn.wolfcode.crm.domain.Role;
import cn.wolfcode.crm.mapper.RoleMapper;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IRoleService;
import cn.wolfcode.crm.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public void saveOrUpdate(Role record) {
        if (record.getId() == null) {
            roleMapper.insert(record);
        } else {
            //删除与permission的关系
            roleMapper.deleteRelationWithPermission(record.getId());
            //删除与menu的关系
            roleMapper.deleteRelationWithMenu(record.getId());
            roleMapper.updateByPrimaryKey(record);
        }

        //新增与permission的关系
        List<Permission> permissions = record.getPermissions();
        if (permissions != null) {
            for (Permission permission : permissions) {
                roleMapper.insertRelationWithPermission(record.getId(),permission.getId());
            }
        }
        //新增与menu的关系
        List<Menu> menus = record.getMenus();
        if (menus != null) {
            for (Menu menu: menus) {
                roleMapper.insertRelationWithMenu(record.getId(),menu.getId());
            }
        }
    }

    @Override
    public Role get(Long id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Role> list() {
        return roleMapper.selectAll();
    }

    @Override
    public PageResult query(QueryObject qo) {
        int count = roleMapper.queryCount(qo);
        if (count == 0) {
            return new PageResult(0, Collections.EMPTY_LIST);
        }
        return new PageResult(count, roleMapper.queryList(qo));
    }

    @Override
    public List<Long> selectByEmployeeId(Long employeeId) {
        return roleMapper.selectByEmployeeId(employeeId);
    }

    @Override
    public List<String> selectNameByEmployeeId(Long employeeId) {
        return roleMapper.selectNameByEmployeeId(employeeId);
    }

}
