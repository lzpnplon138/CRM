package cn.wolfcode.crm.query;

import cn.wolfcode.crm.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
@Getter
public class EmployeeQueryObject extends QueryObject {
    //关键字
    private String keyword;

    //入职时间的起始与结束日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date minDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date maxDate;

    //部门id
    private Long deptId = -1L;

    //要排序的列名
    private String sort;
    //排序规则(升序/降序)
    private String order;

    public String getKeyword() {
        return empty2Null(keyword);
    }

    public Date getMaxDate() {
        return DateUtil.getEndDate(maxDate);
    }
}
