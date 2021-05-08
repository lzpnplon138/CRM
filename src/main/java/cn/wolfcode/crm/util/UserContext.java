package cn.wolfcode.crm.util;


import cn.wolfcode.crm.domain.Employee;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public abstract class UserContext {

    public static Employee getCurrentUser() {
        return (Employee) SecurityUtils.getSubject().getPrincipal();
    }

    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest();
    }

}
