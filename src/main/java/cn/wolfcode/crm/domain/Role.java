package cn.wolfcode.crm.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Role {
    private Long id;

    //角色编码
    private String sn;

    //角色名称
    private String name;

    //权限
    private List<Permission> permissions = new ArrayList<>();
    //菜单
    private List<Menu> menus = new ArrayList<>();

}