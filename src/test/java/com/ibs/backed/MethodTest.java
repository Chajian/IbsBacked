package com.ibs.backed;

import com.ibs.backed.dao.MyBatis;
import com.ibs.backed.data.StringRule;
import com.ibs.backed.data.User;
import com.ibs.backed.util.Translate;
import org.junit.jupiter.api.Test;

public class MethodTest {

    @Test
    public void test(){
        User user = MyBatis.getMyBatis().getRbacDao().selectUserByname("23421123");
//        user.setUsername("23421123");
//        user.setPassword("Aasd23123");
//        MyBatis.getMyBatis().getRbacDao().regUser(user);
//        MyBatis.getMyBatis().commit();
        System.out.println(user.toString());
    }

}
