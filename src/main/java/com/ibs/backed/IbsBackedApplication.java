package com.ibs.backed;

import com.ibs.backed.dao.MyBatis;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.ImportResource;

//@ImportResource(locations = {"classpath:\\kaptcha.xml"})
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@SpringBootApplication
public class IbsBackedApplication {

    public static void main(String[] args) {
        SpringApplication.run(IbsBackedApplication.class, args);
    }

}
