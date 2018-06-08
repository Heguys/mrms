package com.hxminco.mrms.comm.model;


import com.hxminco.mrms.comm.entry.RsStudentInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;


public class LoginManager {

    //private static String CURRENT_USER = LoginManager.class.getName() + "_currentUser";

    public static void setLogin(RsStudentInfo login) {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.getSession().setAttribute("currentUser", login);
        System.out.println(currentUser.getSession().getId());
    }

    public static RsStudentInfo getLogin() {
        Subject currentUser = SecurityUtils.getSubject();
        RsStudentInfo login = (RsStudentInfo) currentUser.getSession().getAttribute("currentUser");
        if (login == null) {
            login = new RsStudentInfo();
            currentUser.getSession().setAttribute("currentUser", login);
        }
        return login;

    }

    public static String getExamCode() {
        return getLogin() == null ? null : getLogin().getExamCode();
    }

}
