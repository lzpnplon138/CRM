package cn.wolfcode.crm.util;

import lombok.Getter;

@Getter
public class JSONResult {
    //操作是否成功
    private boolean success = true;
    //错误信息
    private String msg;

    public JSONResult mark(String msg) {
        this.msg = msg;
        success = false;
        return this;
    }
}
