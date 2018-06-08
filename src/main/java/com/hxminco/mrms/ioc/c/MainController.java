package com.hxminco.mrms.ioc.c;

import com.hxminco.mrms.comm.entry.RsStudentInfo;
import com.hxminco.mrms.comm.entry.Teacher;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class MainController {

    @RequestMapping("/main.html")
    public String gotoMain(){
        Subject subject = SecurityUtils.getSubject();
        Object login = subject.getSession().getAttribute("currentUser");
        if(login != null && login instanceof RsStudentInfo){
            return "student/examRule";
        }else if(login != null && login instanceof Teacher){
                return "teacher/main";
        }else{
            return "/login";
        }
    }
}
