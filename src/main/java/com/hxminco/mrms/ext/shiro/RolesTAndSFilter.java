package com.hxminco.mrms.ext.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * Created by wandering on 2017/8/15.
 */
public class RolesTAndSFilter extends AuthorizationFilter {
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws  IOException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        Subject subject = getSubject(request, response);

        if (subject.getPrincipal() == null) {
            if (ShiroUtils.isAjax(httpRequest)) {
                httpResponse.setContentType("text/json;charset=utf-8");
                httpResponse.getWriter().write("{\"success\":false,\"error\":\"您尚未登录或登录时间过长,请重新登录!\"}");
            } else {
                saveRequestAndRedirectToLogin(request, response);
            }
        } else {
            if (ShiroUtils.isAjax(httpRequest)) {
                httpResponse.setContentType("text/json;charset=utf-8");
                httpResponse.getWriter().write("{\"success\":false,\"error\":\"您没有足够的权限执行该操作!\"}");
            } else {
                String unauthorizedUrl = getUnauthorizedUrl();
                if (StringUtils.hasText(unauthorizedUrl)) {
                    WebUtils.issueRedirect(request, response, unauthorizedUrl);
                } else {
                    WebUtils.toHttp(response).sendError(401);
                }
            }
        }
        return false;
    }

        @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        Subject subject = getSubject(servletRequest, servletResponse);
        String[] rolesArray = (String[]) o;
        if (rolesArray == null || rolesArray.length == 0) {
            return true;
        }
        Set<String> roles = CollectionUtils.asSet(rolesArray);
        boolean ret = subject.hasAllRoles(roles);
        return ret;
    }
}
