package com.soap.app.service.impl;

import com.soap.app.entity.User;
import com.soap.app.mapper.UserDao;
import com.soap.app.service.UserServiceB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImplB implements UserServiceB {

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveUserB(User u) {
            userDao.addUser(u);
        System.out.println("异常了");
            int a=1/0;
    }

}
