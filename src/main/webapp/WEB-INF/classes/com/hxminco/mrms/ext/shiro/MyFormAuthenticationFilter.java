package com.hxminco.mrms.ext.shiro;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Created by Employee on 2017/8/10.
 */
public class MyFormAuthenticationFilter extends FormAuthenticationFilter {
    public static final String DEFAULT_LOGIN_TYPE_PARAM = "loginType";

    private String loginTypeParamName = DEFAULT_LOGIN_TYPE_PARAM;

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        //如果其他过滤器已经,验证失败了,则禁止登陆,不再进行身份验证
        if (request.getAttribute(getFailureKeyAttribute()) != null) {
            return true;
        }
        return super.onAccessDenied(request, response, mappedValue);
    }
    /**
     * 重写该方法,为了将loginType参数保存到token中
     *
     * @param request  请求
     * @param response 响应
     * @return
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        String username = getUsername(request);
        String password = getPassword(request);
        String loginType = getLoginType(request);
        return createToken(username, password, request, response, loginType);
    }

    private AuthenticationToken createToken(String username, String password, ServletRequest request, ServletResponse response, String loginType) {
        boolean rememberMe = isRememberMe(request);
        String host = getHost(request);
        return createToken(username, password, rememberMe, host, loginType);
    }

    private AuthenticationToken createToken(String username, String password, boolean rememberMe, String host, String loginType) {
        return new UsernamePasswordLoginTypeToken(username, password, rememberMe, host, loginType);
    }

    private String getLoginType(ServletRequest request) {
        return WebUtils.getCleanParam(request, getLoginTypeParamName());
    }


    public String getLoginTypeParamName() {
        return loginTypeParamName;
    }

    public void setLoginTypeParamName(String loginTypeParamName) {
        this.loginTypeParamName = loginTypeParamName;
    }
}
