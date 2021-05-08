package cn.wolfcode.crm.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    //登陆失败时访问
    @RequestMapping("/login")
    public String login(){
        return "redirect:/login.jsp";
    }

    //注销
    @RequestMapping("/logout")
    public String logout(){
        return "redirect:/login.jsp";
    }

    //跳转到主页
    @RequestMapping("/index")
    public String index(){
        return "index";
    }
}
