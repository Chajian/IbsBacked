package com.ibs.backed.verify;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ibs.backed.data.User;
import org.springframework.stereotype.Service;

@Service("TokenService")
public class TokenService {
    /**
     * 获取token
     * @param user
     * @return
     */
    public String getToken(User user) {
        String token="";
        token= JWT.create().withAudience(user.getId())
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }
}
