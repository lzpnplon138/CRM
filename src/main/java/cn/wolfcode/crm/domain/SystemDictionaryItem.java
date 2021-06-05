package cn.wolfcode.crm.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SystemDictionaryItem {

    private Long id;

    private String name;

    private String intro;

    private SystemDictionary  parent;
}