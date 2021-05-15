package cn.wolfcode.crm.service;

import cn.wolfcode.crm.domain.Menu;

import java.util.List;

public interface IMenuService {


    /**
     * 查询所有根菜单
     * @return
     */
    List<Menu> selectRootMenus();

    /**
     * 查询该角色的所有菜单的id
     * @param roleId 角色的id
     * @return
     */
    List<Long> selectByRoleId(Long roleId);


    /**
     * 查询登陆用户的对应菜单
     * @return
     */
    List<Menu> selectEmployeeMenu();
}
