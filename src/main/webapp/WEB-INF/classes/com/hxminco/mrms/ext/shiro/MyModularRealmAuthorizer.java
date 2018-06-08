package com.hxminco.mrms.ext.shiro;

import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import java.util.Set;

/**
 * Created by Employee on 2017/9/19.
 */
public class MyModularRealmAuthorizer extends ModularRealmAuthorizer {
    @Override
    public boolean hasRole(PrincipalCollection principals, String roleIdentifier) {
        assertRealmsConfigured();
        Set<String> realmNames = principals.getRealmNames();
        for (Realm realm : getRealms()) {
            if (!(realm instanceof Authorizer)) continue;
            if (realmNames.contains(realm.getName())) {
                if((((Authorizer) realm).hasRole(principals, roleIdentifier))) {
                    return true;
                }
            }
        }
        return false;
    }
}
