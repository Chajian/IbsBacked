package com.ibs.backed.dao;

import com.ibs.backed.data.User;
import org.springframework.stereotype.Service;

@Service("UserDao")
public interface UserDao {

    public User findByUsername(String username);

    public User findUserById(String Id);

}
