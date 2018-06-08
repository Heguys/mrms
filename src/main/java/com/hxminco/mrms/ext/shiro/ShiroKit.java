package com.hxminco.mrms.ext.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;


public class ShiroKit {
    /**
     * 禁止初始化
     */
    private ShiroKit() {
    }
    /**
     * 获取 Subject
     *
     * @return Subject
     */
    protected static Subject getSubject() {
        return SecurityUtils.getSubject();
    }
    /**
     * 验证当前用户是否拥有指定权限
     *
     * @param permission
     *            权限名
     * @return 拥有权限：true，否则false
     */
    public static boolean hasPermission(String permission) {
        return getSubject() != null && permission != null && permission.length() > 0 && getSubject().isPermitted(permission);
    }

    /**
     * 与hasPermission标签逻辑相反，当前用户没有制定权限时，验证通过。
     *
     * @param permission
     *            权限名
     * @return 拥有权限：true，否则false
     */
    public static boolean lacksPermission(String permission) {
        return !hasPermission(permission);
    }

}