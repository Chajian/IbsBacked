package com.ibs.backed.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;

/**
 *
 * jwt token 校验工具
 *
 */
public class JwtUtil {

    /*token令牌存在的时间*/
    private static final long EXPIRE_TIME = 10*60*1000;

    public static String sign(String username,String password,int userId){
        Date date = new Date(System.currentTimeMillis()+EXPIRE_TIME);

        Algorithm algorithm = Algorithm.HMAC256(password);
        HashMap<String,Object> header = new HashMap<>(2);
        header.put("typ","JWT");
        header.put("alg","HS256");
        return JWT.create().withHeader(header).withClaim("userId",userId)
                .withClaim("username",username).withExpiresAt(date).sign(algorithm);
    }

    /**
     * 验证
     * @param token
     * @param password
     * @return
     */
    public static boolean verity(String token,String password){
        try {
            Algorithm algorithm = Algorithm.HMAC256(password);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            return true;
        }
        catch (IllegalArgumentException e){
            return false;
        }
        catch (JWTVerificationException e){
            return false;
        }
    }

    /**
     * 获得token中的信息
     * @param token token
     * @return token中包含的用户名
     */
    public static String getUsername(String token){
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        }
        catch (JWTDecodeException e){
            return null;
        }
    }


}
