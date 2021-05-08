package cn.wolfcode.crm.util;

import lombok.Getter;

@Getter
public class JSONResult {
    private boolean success = true;
    private String msg;

    public JSONResult mark(String msg) {
        this.msg = msg;
        success = false;
        return this;
    }
}
