package com.hxminco.mrms.ext.shiro;

import org.apache.log4j.Logger;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.util.CollectionUtils;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Employee on 2017/8/10.
 */
public class MyModularRealmAuthenticator extends ModularRealmAuthenticator {
    private final Logger logger = Logger.getLogger(MyModularRealmAuthenticator.class);
    private Map<String, Object> definedRealms;

    @Override
    protected AuthenticationInfo doMultiRealmAuthentication(Collection<Realm> realms, AuthenticationToken token) throws AuthenticationException {
        return super.doMultiRealmAuthentication(realms, token);
    }

    /**
     * 判断realms是否匹配,并返回唯一的可用的realm,否则返回空
     *
     * @param realms realm集合
     * @param token  登陆信息
     * @return 返回唯一的可用的realm
     */
//    private Realm getUniqueRealm(Collection<Realm> realms, AuthenticationToken token) {
//        for (Realm realm : realms) {
//            if (realm.supports(token)) {
//                return realm;
//            }
//        }
//        log.error("一个可用的realm都没有找到......");
//        return null;
//    }

    /**
     * 判断登录类型执行操作
     */
    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken) throws AuthenticationException {
        this.assertRealmsConfigured();

        Realm realm = null;

        UsernamePasswordLoginTypeToken token = (UsernamePasswordLoginTypeToken) authenticationToken;
        if (token.getLoginType().equals("teacher")) {
            realm = (Realm) this.definedRealms.get("teacherRealm");
        }
        if (token.getLoginType().equals("student")) {
            realm = (Realm) this.definedRealms.get("studentRealm");
        }
        if (realm == null) {
            return null;
        }

        return this.doSingleRealmAuthentication(realm, authenticationToken);
    }

    @Override
    protected void assertRealmsConfigured() throws IllegalStateException {
        this.definedRealms = this.getDefinedRealms();
        if (CollectionUtils.isEmpty(this.definedRealms)) {
            throw new ShiroException("值传递错误!");
        }
    }

    public Map<String, Object> getDefinedRealms() {
        return this.definedRealms;
    }

    public void setDefinedRealms(Map<String, Object> definedRealms) {
        this.definedRealms = definedRealms;
    }
}
