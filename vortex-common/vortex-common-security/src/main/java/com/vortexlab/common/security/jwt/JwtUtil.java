package com.vortexlab.common.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtil {

    private final SecretKey key;

    private final Long expire;

    public JwtUtil(String secret, Long expire) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.expire = expire;
    }

    /**
     * 生成 JWT Token。
     */
    public String createToken(Long userId) {
        Date now = new Date();

        Date expiration = new Date(now.getTime() + expire);

        return Jwts.builder()
                // 将 userId 写入 Subject，解析时还原
                .setSubject(String.valueOf(userId))
                // 记录签发时间
                .setIssuedAt(now)
                // 设置过期时间
                .setExpiration(expiration)
                // 使用 HS256 算法签名
                .signWith(key)
                .compact();
    }

    /**
     * 解析 Token，
     */
    public Long parseToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return Long.valueOf(claims.getSubject());
    }

    /**
     * 校验 Token，
     */
    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
