package com.xavier.base.util.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JWTVars {

    /**
     * Token 超时时间
     */
    public static Long EXPIRE_TIME;

    /**
     * 秘钥
     */
    public static String SECRET;

    /**
     * Http header中存储Token的字段名
     */
    public static String HEADER_NAME;

    @Value("${jwt.header-name:Authorization}")
    public void setHeaderName(String headerName) {
        HEADER_NAME = headerName;
    }

    @Value("${jwt.expire-time:60000}")
    public void setExpireTime(Long expireTime) {
        EXPIRE_TIME = expireTime;
    }

    @Value("${jwt.secret:MySecret}")
    public void setSecret(String secret) {
        SECRET = secret;
    }
}
