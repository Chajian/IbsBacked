package com.ibs.backed.dao;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * 通过Mybatis获取信息
 * @author captain
 */
public class MyBatis {

    public static MyBatis myBatis;
    private SqlSession sqlSession;

    private MyBatis(){
        try {
            InputStream inputStream = Resources.getResourceAsStream("conf/mybatis-config.xml");
            SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
            SqlSessionFactory factory = builder.build(inputStream);
            sqlSession = factory.openSession();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public UserDao getUserDao(){
        UserDao personDao = sqlSession.getMapper(UserDao.class);
        return personDao;
    }



    public static MyBatis getMyBatis() {
        if(myBatis==null)
            myBatis = new MyBatis();
        return myBatis;
    }

    public void commit(){
        sqlSession.commit();
    }
}