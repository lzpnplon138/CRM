package cn.wolfcode.crm.util;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 对controller的增强
 */
@ControllerAdvice
public class HandlerExceptionUtil {

    /**
     * 处理无权限异常
     */
    @ExceptionHandler(UnauthorizedException.class)
    public void handlerException(HttpServletResponse response, HandlerMethod method) throws IOException {
        //区分是否ajax请求(ResponseBody注解)
        ResponseBody annotation = method.getMethodAnnotation(ResponseBody.class);
        if (annotation == null) {
            //不是ajax请求,直接重定向跳转页面
            response.sendRedirect("/static/common/nopermission.jsp");
        } else {
            //是ajax请求,返回json
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSONUtil.toJSON(new JSONResult().mark("你没有权限操作!")));
        }
    }
}
