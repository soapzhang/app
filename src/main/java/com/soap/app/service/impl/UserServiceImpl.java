package com.soap.app.service.impl;

import com.soap.app.entity.User;
import com.soap.app.mapper.UserDao;
import com.soap.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Override
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public void saveUser(User u) {
            userDao.addUser(u);
    }
}
