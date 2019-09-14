package com.xavier.base.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * JWT生成校验
 *
 * @author NewGr8Player
 */
@Slf4j
@Component
public class JWTGen {

    public static final String CLAIM_FIELD_NAME = "username";

    /**
     * <pre>
     * 生成 token,并放入Redis缓存中
     * 当此用户再次异地登录时继续使用原有token
     * 保持单点登录去掉 {@code @Cacheable} 注解即可
     * 原理：
     * 每次生成token前先在缓存中查询
     * 如果缓存中存在username的token则取出使用
     * 不存在则调用生成方法，并将返回值放入缓存中
     * 缓存中key默认为传入参数的组合
     * </pre>
     *
     * @param username 用户名
     * @return 加密的token
     */
    @Cacheable(cacheNames = {"token"})
    public String createToken(String username) {
        try {
            Date date = new Date(System.currentTimeMillis() + JWTVars.EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(JWTVars.SECRET);
            return JWT.create()
                    /* 附带username信息 */
                    .withClaim(CLAIM_FIELD_NAME, username)/* 载荷部分包含的信息 */
                    /* 到期时间 */
                    .withExpiresAt(date)
                    /* 创建一个新的JWT，并使用给定的算法进行标记 */
                    .sign(algorithm);
        } catch (Exception e) {
            log.error("Token创建异常。", e);
            return null;
        }
    }

    /**
     * 校验 token 是否正确
     *
     * @param token    密钥
     * @param username 用户名
     * @return 是否正确
     */
    public boolean verify(String token, String username) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(JWTVars.SECRET);
            /* 在token中附带了username信息 */
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim(CLAIM_FIELD_NAME, username)
                    .build();
            /* 验证 token */
            verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 获得token中的信息，无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(CLAIM_FIELD_NAME).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }
}
