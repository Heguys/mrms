package com.hxminco.mrms.ext.shiro;

import com.hxminco.mrms.comm.entry.RsStudentInfo;
import com.hxminco.mrms.comm.entry.Teacher;
import com.hxminco.mrms.comm.model.LoginManager;
import com.hxminco.mrms.comm.model.TeacherLoginManager;
import com.hxminco.mrms.comm.utils.PasswordUtil;
import com.hxminco.mrms.ioc.s.RsStudentInfoService;
import com.hxminco.mrms.ioc.s.TeacherService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

/**
 * Created by wandering on 2017/8/15.
 */
public class UserRealm extends AuthorizingRealm {
    /**
     * 用户数据DAO
     */
    @Autowired
    private RsStudentInfoService studentInfoService;

    /**
     * 用户数据DAO
     */
    @Autowired
    private TeacherService teacherService;
    /**
     * 支持的登陆类型
     */
    private String supportedLoginType;

    /**
     * 授权验证
     *
     * @param principals 认证人
     * @return 授权信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();

        System.out.println(username);

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        if("teacher".equals(supportedLoginType)){
            authorizationInfo.addRole("teacher");
            if("admin".equals(username)){
            authorizationInfo.addRole("admin");
            }
        }else{
            authorizationInfo.addRole("student");
        }
        return authorizationInfo;
    }


    /**
     * 用户认证
     *
     * @param token 令牌
     * @return 认证信息
     * @throws org.apache.shiro.authc.AuthenticationException 认证失败
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        UsernamePasswordLoginTypeToken upToken = (UsernamePasswordLoginTypeToken)token;
        this.supportedLoginType = ((UsernamePasswordLoginTypeToken) token).getLoginType();
        String username = upToken.getUsername().trim();
        String password = "";
        if (upToken.getPassword() != null) {
            password = new String(upToken.getPassword());
        }

        if("teacher".equals(supportedLoginType)){
            Teacher teacher = teacherService.getTeacherByLoginName(username);

            if (teacher == null) {
                throw new UnknownAccountException();//没找到帐号
            } else if(!PasswordUtil.matches(teacher.getPassword(), username, password, null)){
                //logger.info("登录[{}]用户密码错误", username);
                throw new IncorrectCredentialsException();
            }
            TeacherLoginManager.setLogin(teacher);
            return new SimpleAuthenticationInfo(
                    teacher.getUsername(),
                    password.toCharArray(),
                    getName());
        }else {
            RsStudentInfo student = studentInfoService.getStudentByLoginIdentityId(username);
            if (student == null) {
                throw new UnknownAccountException();//没找到帐号
            } else if(!Objects.equals(student.getExamCode(), password)){
                //logger.info("登录[{}]用户密码错误", username);
                throw new IncorrectCredentialsException();
            }
            LoginManager.setLogin(student);
            return new SimpleAuthenticationInfo(
                    student.getIdentityId(), //用户名
                    student.getExamCode(), //密码
                    getName()  //realm name
            );
        }
    }


    public String getSupportedLoginType() {
        return supportedLoginType;
    }
    /**
     *spring注入
     */
    public void setSupportedLoginType(String supportedLoginType) {
        this.supportedLoginType = supportedLoginType;
    }
}