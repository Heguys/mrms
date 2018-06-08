package com.hxminco.mrms.ext.shiro;

import com.hxminco.mrms.comm.entry.RsStudentInfo;
import com.hxminco.mrms.comm.model.LoginManager;
import com.hxminco.mrms.ioc.s.RsStudentInfoService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

/**
 * Created by Employee on 2017/8/10.
 */
public class StudentRealm extends AuthorizingRealm {
    /**
     * 用户数据DAO
     */
    @Autowired
    private RsStudentInfoService studentInfoService;
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
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addRole("student");
        return authorizationInfo;
    }


    /**
     * 用户认证
     *
     * @param token 令牌
     * @return 认证信息
     * @throws AuthenticationException 认证失败
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        UsernamePasswordLoginTypeToken upToken = (UsernamePasswordLoginTypeToken)token;
        String username = upToken.getUsername().trim();
        String password = "";
        if (upToken.getPassword() != null) {
            password = new String(upToken.getPassword());
        }
        RsStudentInfo student = studentInfoService.getStudentByLoginExamCode(username);
        if (student == null) {
            throw new UnknownAccountException();//没找到帐号
        } else if(!Objects.equals(student.getIdentityId(), password)){
            //logger.info("登录[{}]用户密码错误", username);
            throw new IncorrectCredentialsException();
        }
        LoginManager.setLogin(student);
        return new SimpleAuthenticationInfo(
                student.getExamCode(), //用户名
                student.getIdentityId(),//密码
                getName()  //realm name
        );
    }

    public boolean supports(AuthenticationToken token) {
        if (token instanceof UsernamePasswordLoginTypeToken) {
            UsernamePasswordLoginTypeToken usernamePasswordLoginTypeToken = (UsernamePasswordLoginTypeToken) token;
            return getSupportedLoginType().equals(usernamePasswordLoginTypeToken.getLoginType());
        }
        return false;
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
