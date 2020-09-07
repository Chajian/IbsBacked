package com.ibs.backed.verify;

import com.ibs.backed.annotation.RoleRequired;
import com.ibs.backed.dao.MyBatis;
import com.ibs.backed.dao.RbacDao;
import com.ibs.backed.data.Role;
import com.ibs.backed.data.User;
import com.ibs.backed.util.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;


/**
 * 授权认证拦截器
 *
 */
@Component
public class AuthenrizationInterceptor implements HandlerInterceptor {

    RbacDao rbacDao = MyBatis.getMyBatis().getRbacDao();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //        如果不是映射到方法直接通过
        String token = request.getHeader("token");
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        if(method.isAnnotationPresent(RoleRequired.class)){
            if(token == null){
                throw new RuntimeException("无token,请重新登陆");
            }

            RoleRequired roleRequired = method.getAnnotation(RoleRequired.class);
            String role = roleRequired.value();
            String username = JwtUtil.getUsername(token);
            User user = rbacDao.selectUserByname(username);
            if(user == null){
                throw new RuntimeException("用户不存在，请重新登陆");
            }
            List<Integer> roles = rbacDao.selectUserRole(user.getId());
            for(int roleid:roles){
                Role role1 = rbacDao.selectRoleByid(roleid);
                if(role1.getName().equals(role)){
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
