package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.Menu;
import java.util.List;

public interface MenuMapper {

    int insert(Menu record);

    int updateByPrimaryKey(Menu record);

    int deleteByPrimaryKey(Long id);

    Menu selectByPrimaryKey(Long id);

    List<Menu> selectAll();

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
     * 查询该用户的所有菜单的id
     * @param employeeId 用户的id
     */
    List<Long> selectMenuByEmployeeId(Long employeeId);
}