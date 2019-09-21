package com.xavier.base.util.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.xavier.base.util.jwt.JWTVars.SECRET;

/**
 * JWT生成校验
 *
 * @author NewGr8Player
 */
@Slf4j
@Component
public class JWTGen {

    public static final String CLAIM_FIELD_USERNAME = "username";

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
     * @param uid 用户uid
     * @return 加密的token
     */
    //@Cacheable(cacheNames = {"token"})
    public String createToken(String uid) {
        try {
            Date date = new Date(System.currentTimeMillis() + JWTVars.EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            return JWT.create()
                    /* 载荷部分包含的信息 */
                    .withClaim(CLAIM_FIELD_USERNAME, uid)
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
     * @param token           密钥
     * @param claimFieldValue 用户id
     * @return 是否正确
     */
    public boolean verify(String token, String claimFieldValue) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            /* 在token中附带了username信息 */
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim(CLAIM_FIELD_USERNAME, claimFieldValue)
                    .build();
            /* 验证 token */
            verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 验证token是否失效
     *
     * @param token
     * @return true:过期   false:没过期
     */
    public boolean isExpired(String token) {
        try {
            final Date expiration = getExpiresAt(token);
            return expiration.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 获得token中的信息，无需secret解密也能获得
     *
     * @return token中包含的UID
     */
    public String getClaimField(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(CLAIM_FIELD_USERNAME).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获得token中的信息，无需secret解密也能获得
     *
     * @return token过期时间
     */
    public Date getExpiresAt(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getExpiresAt();
        } catch (JWTDecodeException e) {
            return new Date();
        }
    }
}
