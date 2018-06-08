package com.hxminco.mrms.ext.shiro;

/**
 * Created by Employee on 2017/8/10.
 */

import com.hxminco.mrms.comm.entry.Teacher;
import com.hxminco.mrms.comm.model.TeacherLoginManager;
import com.hxminco.mrms.comm.utils.PasswordUtil;
import com.hxminco.mrms.ioc.s.TeacherService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
/**
 * Created by Employee on 2017/8/10.
 */
public class TeacherRealm extends AuthorizingRealm {
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

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addRole("teacher");
        if("admin".equals(username)){
            authorizationInfo.addRole("admin");
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
        String username = upToken.getUsername().trim();
        String password = "";
        if (upToken.getPassword() != null) {
            password = new String(upToken.getPassword());
        }

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
                getName()
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
