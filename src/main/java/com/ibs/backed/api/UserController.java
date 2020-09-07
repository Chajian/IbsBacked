package com.ibs.backed.api;

import com.ibs.backed.annotation.TokenRequired;
import com.ibs.backed.dao.MyBatis;
import com.ibs.backed.dao.RbacDao;
import com.ibs.backed.dao.ResponseBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * 用户接口
 * 接口权限限制为user
 */
@RestController()
@RequestMapping("/api")
@TokenRequired()
public class UserController {

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    private RbacDao userDao = MyBatis.getMyBatis().getRbacDao();

    @RequestMapping("/userinfo")
    @RequiresRoles("user")
    public ResponseBean getUserInfo(String username){
        return new ResponseBean(200,"成功咯",username);
    }

    @RequestMapping("/admininfo")
    @RequiresRoles("admin")
    public ResponseBean getAdminInfo(String username){
        return new ResponseBean(200,"管理员!",username);
    }

}
