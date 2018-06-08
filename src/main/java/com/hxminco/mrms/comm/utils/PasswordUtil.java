package com.hxminco.mrms.comm.utils;


import httl.util.StringUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;

public class PasswordUtil {

    public static final String ALGORITHM_NAME = "md5";

    public static boolean matches(String Md5PassWord, String username, String password, String salt) {
        if(StringUtils.isBlank(Md5PassWord) || StringUtils.isBlank(username) || StringUtils.isBlank(password)){
            return false;
        }
        return Md5PassWord.equals(encrypt(username, password, salt));
    }

    public static String encrypt(String username, String password) {
        return encrypt(username, password, getSalt());
    }

    public static String encrypt(String username, String password, String salt) {
        return new SimpleHash(ALGORITHM_NAME, password, username + salt).toHex();
    }

    public static String getSalt() {
        return new SecureRandomNumberGenerator().nextBytes().toHex();
    }

    public static void main(String[] args) {
        String salt = getSalt();
        System.out.println(salt);
        System.out.println(encrypt("admin", "1qaz@WSX", null));
        System.out.println(encrypt("kaoshijigou1", "123456", null));
        String uuid = StringUtil.getUUID();
        System.out.println(uuid);
        System.out.println("我是谁");
    }
}
