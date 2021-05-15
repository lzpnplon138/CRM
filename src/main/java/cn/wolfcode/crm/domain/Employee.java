package cn.wolfcode.crm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
public class Employee {
    private Long id;

    //用户名
    private String username;

    //真实名称
    private String realname;

    //密码
    private String password;

    //电话
    private String tel;

    //邮箱
    private String email;

    //所在部门
    private Department dept;

    //入职时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date hireDate;

    //状态(在职/离职)
    private Boolean state;

    //是否超管
    private Boolean admin;

    //角色
    private List<Role> roles = new ArrayList<>();

}