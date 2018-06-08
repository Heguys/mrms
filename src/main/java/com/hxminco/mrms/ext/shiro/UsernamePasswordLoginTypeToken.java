package com.hxminco.mrms.ext.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * Created by Employee on 2017/8/10.
 */
public class UsernamePasswordLoginTypeToken extends UsernamePasswordToken {
    private String loginType;

    public UsernamePasswordLoginTypeToken(String username, String password, boolean rememberMe, String host, String loginType) {
        super(username, password, rememberMe, host);
        this.loginType = loginType;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }
}
