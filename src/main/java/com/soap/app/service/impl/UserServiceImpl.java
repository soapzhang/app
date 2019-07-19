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
            //事务不会起作用,事务加在service层，service层结束，那么数据库完成事务提交
            //而不是请求线程结束，数据库事务完成提交
            try {
                int a = 1/0;
            }catch (Exception e){

            }
    }
}
