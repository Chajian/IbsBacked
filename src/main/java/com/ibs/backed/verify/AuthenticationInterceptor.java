package com.ibs.backed.verify;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.ibs.backed.annotation.TokenRequired;
import com.ibs.backed.dao.MyBatis;
import com.ibs.backed.dao.RbacDao;
import com.ibs.backed.data.User;
import com.ibs.backed.util.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;


/**
 * token 验证拦截器
 * @author xml
 * @version 1.0
 */
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    RbacDao rbacDao = MyBatis.getMyBatis().getRbacDao();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从http头中取出token
        String token = request.getHeader("token");

//        如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        //检查一下有没有Tokenrequired注解
        if(method.isAnnotationPresent(TokenRequired.class)){
            TokenRequired userLoginToken = method.getAnnotation(TokenRequired.class);
            if(userLoginToken.required()){
                //执行认证
                if(token == null){
                    throw new RuntimeException("无token,请重新登陆");
                }
                int userid;
                try{
                    userid = JWT.decode(token).getClaim("userId").asInt();
                }
                catch (JWTDecodeException e){
                    throw new RuntimeException("401");
                }
                System.out.println(AuthenticationInterceptor.class+"    userid:    "+userid);
                User user = rbacDao.selectUserByid(userid);
                if(user == null){
                    throw new RuntimeException("用户不存在，请重新登陆");
                }

                //验证token
                try{
                    if(!JwtUtil.verity(token,user.getPassword())){
                        throw new RuntimeException("无效的令牌");
                    }
                }
                catch (JWTVerificationException e){
                    throw new RuntimeException("401");
                }
                return true;
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("请求handle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("请求After");
    }
}
