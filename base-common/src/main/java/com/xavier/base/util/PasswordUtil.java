package com.xavier.common.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * 密码加密工具类
 *
 * @author NewGr8Player
 */
public class PasswordUtil {

    /**
     * 获取加密后的密码
     *
     * @param password
     * @return
     */
    public static String encryptPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * 密码校验
     *
     * @param password  明文密码
     * @param hashedPwd 加密后的密码
     * @return
     */
    public static boolean passwordValidator(String password, String hashedPwd) {
        return BCrypt.checkpw(password, hashedPwd);
    }
}
