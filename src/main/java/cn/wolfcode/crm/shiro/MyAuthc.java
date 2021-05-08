package cn.wolfcode.crm.shiro;

import cn.wolfcode.crm.util.JSONResult;
import cn.wolfcode.crm.util.JSONUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class MyAuthc extends FormAuthenticationFilter {
    //登陆成功后
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        //返回json
        response.getWriter().print(JSONUtil.toJSON(new JSONResult()));
        //return false 不需要执行后面的过滤器或者Controller的方法
        return false;
    }

    //登陆失败后
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        //打印异常信息
        e.printStackTrace();
        String msg = "其他异常情况,请联系管理员!";
        //判断异常类型,响应不同的异常信息给用户
        if (e instanceof UnknownAccountException) {
            msg = "用户名不存在!";
        } else if (e instanceof IncorrectCredentialsException) {
            msg = "密码错误";
        }
        //返回json
        try {
            response.getWriter().print(JSONUtil.toJSON(new JSONResult().mark(msg)));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return false;
    }

    //判断认证是否通过(是否登陆)
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        //如果是login.do的请求,才执行下面逻辑
        if (isLoginRequest(request, response)) { //isLoginRequest：判断是否是登录请求
            //判断是否已经登陆,如果已经登陆,则先注销,再登陆
            Subject subject = SecurityUtils.getSubject();
            if (subject.isAuthenticated()) {
                subject.logout();
            }
        }
        return super.isAccessAllowed(request, response, mappedValue);
    }
}
