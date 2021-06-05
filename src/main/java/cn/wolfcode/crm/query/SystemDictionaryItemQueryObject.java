package cn.wolfcode.crm.query;

import cn.wolfcode.crm.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
@Getter
public class SystemDictionaryItemQueryObject extends QueryObject {

    private Long parentId;
}
