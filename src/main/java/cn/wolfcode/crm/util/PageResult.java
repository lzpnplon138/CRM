package cn.wolfcode.crm.util;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PageResult {
    private int total; //总记录数
    private List<?> rows; //记录的集合
}
