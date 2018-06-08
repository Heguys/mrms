package com.hxminco.mrms.service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by spiderman on 2018/3/23.
 */
public class IPUtil {
    public static String getIp(HttpServletRequest request){
    String ip = request.getHeader("X-Forwarded-For");
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
    } else if (ip.length() > 15) {
        String[] ips = ip.split(",");
        for (int index = 0; index < ips.length; index++) {
            String strIp = (String) ips[index];
            if (!("unknown".equalsIgnoreCase(strIp))) {
                ip = strIp;
                break;
            }
        }
    }
    return ip;
    }

}
