package com.ibs.backed;

import com.ibs.backed.dao.MyBatis;
import com.ibs.backed.dao.UserDao;
import com.ibs.backed.data.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class IbsBackedApplicationTests {

    @Test
    void contextLoads() {
        UserDao userDao = MyBatis.getMyBatis().getUserDao();
        User user = userDao.findByUsername("990077");
        System.out.println(user.toString());

    }

}
