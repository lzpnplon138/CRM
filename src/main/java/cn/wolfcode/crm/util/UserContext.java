package cn.wolfcode.crm.util;


import cn.wolfcode.crm.domain.Employee;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public abstract class UserContext {

    //获取当前登陆的用户
    public static Employee getCurrentUser() {
        return (Employee) SecurityUtils.getSubject().getPrincipal();
    }

    //获取请求对象
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest();
    }

}
