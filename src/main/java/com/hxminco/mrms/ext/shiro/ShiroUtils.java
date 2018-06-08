package com.hxminco.mrms.ext.shiro;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Employee on 2017/9/27.
 */
public class ShiroUtils {
    public static boolean isAjax(HttpServletRequest request) {
        String requestedWith = request.getHeader("x-requested-with");
        if (requestedWith != null && requestedWith.equalsIgnoreCase("XMLHttpRequest")) {
            return true;
        } else {
            return false;
        }
    }
}
