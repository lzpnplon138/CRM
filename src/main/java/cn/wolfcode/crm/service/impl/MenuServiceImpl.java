package cn.wolfcode.crm.service.impl;

import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.domain.Menu;
import cn.wolfcode.crm.mapper.MenuMapper;
import cn.wolfcode.crm.service.IMenuService;
import cn.wolfcode.crm.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class MenuServiceImpl implements IMenuService {
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public void saveOrUpdate(Menu record) {
        if (record.getId() == null) {
            menuMapper.insert(record);
        } else {
            menuMapper.updateByPrimaryKey(record);
        }
    }

    @Override
    public Menu get(Long id) {
        return menuMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Menu> list() {
        return menuMapper.selectAll();
    }

    @Override
    public List<Menu> selectRootMenus() {
        return menuMapper.selectRootMenus();
    }

    @Override
    public List<Long> selectByRoleId(Long roleId) {
        return menuMapper.selectByRoleId(roleId);
    }

    @Override
    public List<Menu> selectEmployeeMenu() {
        //获取所有根菜单
        List<Menu> menus = menuMapper.selectRootMenus();
        //获取当前登陆用户
        Employee user = UserContext.getCurrentUser();
        //不是超管则查询对应菜单,否则直接返回所有菜单
        if (!user.getAdmin()) {
            //获取当前用户的菜单的id集合
            List<Long> ids = menuMapper.selectMenuByEmployeeId(user.getId());
            //从所有菜单中删除用户不该有的菜单
            deleteNoPermissionMenu(menus,ids);
        }
        return menus;
    }

    private void deleteNoPermissionMenu(List<Menu> menus, List<Long> ids) {
        //遍历菜单,删除用户无权访问的菜单
        Iterator<Menu> iterator = menus.iterator();
        while (iterator.hasNext()) {
            Menu menu = iterator.next();
            if (!ids.contains(menu.getId())) {
                //无权访问的菜单,删除
                iterator.remove();
                //删除之后直接结束此次循环,不需要递归判断子菜单
                continue;
            }
            //递归判断子菜单
            List<Menu> children = menu.getChildren();
            if (children.size() > 0) {//注意,不能判断不为null
                deleteNoPermissionMenu(children, ids);
            }
        }
    }


}
