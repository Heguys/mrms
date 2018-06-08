package com.hxminco.mrms.comm.model;
import com.hxminco.mrms.comm.entry.Teacher;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * Created by Employee on 2017/8/9.
 */
public class TeacherLoginManager {

    //private static String CURRENT_USER = TeacherLoginManager.class.getName() + "_currentUser";

    public static void setLogin(Teacher login) {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.getSession().setAttribute("currentUser", login);
        System.out.println(currentUser.getSession().getId());
    }

    public static Teacher getLogin() {
        Subject currentUser = SecurityUtils.getSubject();
        Teacher login = (Teacher) currentUser.getSession().getAttribute("currentUser");
        if (login == null) {
            login = new Teacher();
            currentUser.getSession().setAttribute("currentUser", login);
        }
        return login;

    }

    public static String getUsername() {
        return getLogin() == null ? null : getLogin().getUsername();
    }

}
