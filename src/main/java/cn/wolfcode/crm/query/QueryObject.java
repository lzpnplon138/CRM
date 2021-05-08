package cn.wolfcode.crm.query;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QueryObject {
    private int page = 1; //当前页
    private int rows = 10; //pageSize

    public int getStart() {
        return (page - 1) * rows;
    }

    //将空字符串转为null
    protected String empty2Null(String s) {
        return s != null && s.trim().length() > 0 ? s : null;
    }
}
