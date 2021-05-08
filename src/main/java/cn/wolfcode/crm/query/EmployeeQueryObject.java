package cn.wolfcode.crm.query;

import cn.wolfcode.crm.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
@Getter
public class EmployeeQueryObject extends QueryObject {
    private String keyword;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date minDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date maxDate;
    private Long deptId = -1L;

    //排序
    private String sort;
    private String order;

    public String getKeyword() {
        return empty2Null(keyword);
    }

    public Date getMaxDate() {
        return DateUtil.getEndDate(maxDate);
    }
}
