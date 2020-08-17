package com.ibs.backed.api;

import com.ibs.backed.dao.MyBatis;
import com.ibs.backed.dao.UserDao;
import com.ibs.backed.data.User;
import com.ibs.backed.verify.TokenService;
import com.ibs.backed.verify.UserLoginToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping("api")
public class UserApi {
    UserDao userService;
    @Autowired
    TokenService tokenService;
    //登录
    @PostMapping("/login")
    public Object login(@RequestBody User user){
        userService = MyBatis.getMyBatis().getUserDao();
        JSONObject jsonObject=new JSONObject();
        User userForBase=userService.findByUsername(user.getUsername());
        if(userForBase==null){
            jsonObject.put("message","登录失败,用户不存在");
            return jsonObject;
        }else {
            if (!userForBase.getPassword().equals(user.getPassword())){
                jsonObject.put("message","登录失败,密码错误");
                return jsonObject;
            }else {
                String token = tokenService.getToken(userForBase);
                jsonObject.put("token", token);
                jsonObject.put("user", userForBase);
                return jsonObject;
            }
        }
    }
    @UserLoginToken
    @GetMapping("/getMessage")
    public String getMessage(){
        return "你已通过验证";
    }
}