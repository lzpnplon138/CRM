package cn.wolfcode.crm.domain;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Menu {
    private Long id;

    private String text;

    private String url;

    //上级菜单
    private Menu parent;

    //子菜单
    private List<Menu> children = new ArrayList<>();

}