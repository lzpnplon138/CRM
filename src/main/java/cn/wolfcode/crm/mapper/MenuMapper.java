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
     * @param roleId
     * @return
     */
    List<Long> selectByRoleId(Long roleId);

    /**
     * 根据用户的id查询对应菜单的id
     * @param employeeId
     */
    List<Long> selectMenuByEmployeeId(Long employeeId);
}