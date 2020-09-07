package com.ibs.backed;

import com.ibs.backed.dao.MyBatis;
import com.ibs.backed.dao.RbacDao;
import com.ibs.backed.data.StringRule;
import com.ibs.backed.data.User;
import com.ibs.backed.util.Translate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class IbsBackedApplicationTests {

    @Test
    void contextLoads() {
        String account = "j23";
        System.out.println(Translate.hasMatch(StringRule.VERIFYACCOUNT.getRule(),account));
    }

}
