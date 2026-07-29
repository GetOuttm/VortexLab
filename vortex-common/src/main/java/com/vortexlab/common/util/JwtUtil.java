package com.vortexlab.common.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * JWT 工具类，负责 Token 的生成与解析。
 * <p>
 * 使用 HS256 对称加密算法，Subject 中存储 userId，
 * 供 vortex-user 登录签发 Token，vortex-gateway 统一校验。
 * </p>
 */
public class JwtUtil {

    /** JWT 签名密钥（生产环境应移至配置文件，禁止硬编码） */
    private static final String SECRET =
            "vortexlab-vortexlab-vortexlab-vortexlab";

    /** 由密钥派生的 HMAC-SHA 签名 Key，供 jjwt 0.11.x 使用 */
    private static final SecretKey KEY =
            Keys.hmacShaKeyFor(SECRET.getBytes());

    /** Token 有效期：24 小时（毫秒） */
    private static final long EXPIRE = 1000 * 60 * 60 * 24;

    /**
     * 根据用户 ID 生成 JWT Token。
     *
     * @param userId 用户唯一标识
     * @return 签名后的 JWT 字符串
     */
    public static String createToken(Long userId) {

        return Jwts.builder()
                // 将 userId 写入 Subject，解析时还原
                .setSubject(String.valueOf(userId))
                // 记录签发时间
                .setIssuedAt(new Date())
                // 设置过期时间
                .setExpiration(
                        new Date(System.currentTimeMillis() + EXPIRE)
                )
                // 使用 HS256 算法签名
                .signWith(
                        SignatureAlgorithm.HS256,
                        SECRET
                )
                .compact();
    }

    /**
     * 解析并校验 JWT Token，提取用户 ID。
     * <p>
     * 校验内容包括：签名是否正确、Token 是否过期。
     * 校验失败时抛出 {@link JwtException} 及其子类异常。
     * </p>
     *
     * @param token 客户端传入的 JWT 字符串
     * @return 解析出的用户 ID
     * @throws JwtException Token 无效、过期或签名不匹配时抛出
     */
    public static Long parseToken(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();

        return Long.valueOf(claims.getSubject());
    }
}
