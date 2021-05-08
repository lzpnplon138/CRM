package cn.wolfcode.crm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class SystemLog {
    private Long id;

    //操作用户
    private Employee opUser;

    //操作时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date opTime;

    //登录IP
    private String opIp;

    //功能
    private String function;

    //参数
    private String params;

}