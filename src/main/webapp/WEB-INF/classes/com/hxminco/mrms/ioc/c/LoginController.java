package com.hxminco.mrms.ioc.c;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {


    private String defaultPage = "login";

    private Map<String, String> loginErrorMsg;

    @PostConstruct
    public void initLoginErrorMsg(){
        loginErrorMsg = new HashMap<>();
        loginErrorMsg.put(UnknownAccountException.class.getName(), "用户不存在. ");
        loginErrorMsg.put(IncorrectCredentialsException.class.getName(), "登录密码错误. ");
        loginErrorMsg.put(ExcessiveAttemptsException.class.getName(), "登录失败次数过多.");
        loginErrorMsg.put(LockedAccountException.class.getName(), "用户已被锁定. ");
        loginErrorMsg.put(DisabledAccountException.class.getName(), "用户已被禁用. ");
        loginErrorMsg.put(ExpiredCredentialsException.class.getName(), "用户已过期. ");
        loginErrorMsg.put(UnauthorizedException.class.getName(), "您没有得到相应的授权. ");
        loginErrorMsg = Collections.unmodifiableMap(loginErrorMsg);
    }

    @RequestMapping(value = "/login.html", method= RequestMethod.GET)
    public String loginPage(RedirectAttributes attributes,HttpServletRequest request) {
        String message = request.getParameter("message");
            attributes.addAttribute("message",message);
        return logout();
    }

    @RequestMapping(value = "/login.html", method = RequestMethod.POST)
    public String submitLoginForm(HttpServletRequest request, Model model) {
        String errorClassName = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
        String authticationError = null;

        if(loginErrorMsg.containsKey(errorClassName)){
            authticationError = loginErrorMsg.get(errorClassName);
        } else {
            authticationError = "未知错误.";
        }
        model.addAttribute("message", authticationError);
        return defaultPage;
    }

    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        if(subject != null){
            subject.logout();
        }
        return "redirect:/doLogin.html";
    }

    @RequestMapping(value = "/unauthorized.html")
    public String unauthorized() {

        return "unauthorized";
    }

    @RequestMapping(value = "/doLogin.html")
    public String toLoginPage() {

        return defaultPage;
    }
}
