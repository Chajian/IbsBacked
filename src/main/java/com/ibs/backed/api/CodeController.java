package com.ibs.backed.api;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.ibs.backed.dao.MyBatis;
import com.ibs.backed.dao.RbacDao;
import com.ibs.backed.dao.ResponseBean;
import com.ibs.backed.data.StringRule;
import com.ibs.backed.data.User;
import com.ibs.backed.util.JwtUtil;
import com.ibs.backed.util.Translate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;

/**
 * 验证码生成和登陆验证
 */
@RestController()
@RequestMapping("verify")
public class CodeController {

    @Autowired
    private Producer patchaProducer = null;

    private static final Logger logger = LogManager.getLogger(CodeController.class);

    private RbacDao userDao = MyBatis.getMyBatis().getRbacDao();

    @RequestMapping("/{id}/kaptcha")
    public void getKaptchaImage(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response)throws Exception{
        HttpSession session = request.getSession();
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        //生成验证码
        String capText = patchaProducer.createText();
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
        //向客户端写出
        BufferedImage bi = patchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
    }

    @CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
    @PostMapping("login")
    public ResponseBean login(@RequestParam("username") String username, @RequestParam("password") String password){
        System.out.println("登陆界面log"+username+"   :   "+password);
        String token = null;
        User user = userDao.selectUserByname(username);
        System.out.println(user.toString());
        if(user == null){
            return new ResponseBean(404,"用户不存在",null);
        }
        else{
            if(!user.getPassword().equals(password))
                return new ResponseBean(404,"密码错误",null);
            else
                token = JwtUtil.sign(user.getUsername(),user.getPassword(),user.getId());
        }
        return new ResponseBean(200,"登陆成功!",token);
    }

    @PostMapping("reg")
    public ResponseBean register(@RequestParam("username")String username,@RequestParam("password")String password){
        if(Translate.hasMatch(StringRule.VERIFYPASS.getRule(),password)&&Translate.hasMatch(StringRule.VERIFYACCOUNT.getRule(),username)){
            User user = userDao.selectUserByname(username);
            if(user == null){
                user = new User();
                user.setPassword(password);
                user.setUsername(username);
                userDao.regUser(user);
                MyBatis.getMyBatis().commit();
                return new ResponseBean(200,"用户注册成功",null);
            }
            else{
                return new ResponseBean(404,"用户已存在",null);
            }
        }
        return new ResponseBean(404,"账号或密码不符合规则!",null);
    }


}
