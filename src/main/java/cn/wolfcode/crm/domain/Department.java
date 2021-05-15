package cn.wolfcode.crm.domain;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Department {
    private Long id;

    //部门编码
    private String sn;

    //部门名称
    private String name;

    //部门状态
    private Boolean state;


}