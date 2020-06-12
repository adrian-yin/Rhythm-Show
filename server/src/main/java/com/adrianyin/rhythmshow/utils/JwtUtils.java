package com.adrianyin.rhythmshow.utils;

import com.adrianyin.rhythmshow.domain.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

// 提供生成token验证token的方法
public class JwtUtils {

    private static final long EXPIRE_TIME= 10 * 60 * 60 * 1000;
    // 密钥
    private static final String TOKEN_SECRET="jx9X-HC?";

    // 生成token
    public static String sign(User user) {
        String token = null;
        try {
            Date expiresAt = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            token = JWT.create()
                    .withIssuer("auth0")
                    .withClaim("id", user.getId())
                    .withExpiresAt(expiresAt)
                    .sign(Algorithm.HMAC256(TOKEN_SECRET));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }

    // 验证token
    public static boolean verify(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("auth0").build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 解析token
    public static int getIdByToken(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("id").asInt();
        } catch (JWTDecodeException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }
}
