package cn.wolfcode.crm.service;

import cn.wolfcode.crm.domain.Menu;
import cn.wolfcode.crm.domain.Menu;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.util.PageResult;

import java.util.List;

public interface IMenuService {
    void saveOrUpdate(Menu record);

    Menu get(Long id);

    List<Menu> list();


    /**
     * 查询所有根菜单
     * @return
     */
    List<Menu> selectRootMenus();

    /**
     * 查询该角色的所有菜单的id
     * @param roleId
     * @return
     */
    List<Long> selectByRoleId(Long roleId);


    /**
     * 查询登陆用户的菜单
     * @return
     */
    List<Menu> selectEmployeeMenu();
}
