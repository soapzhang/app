package com.soap.app.service;

import com.soap.app.entity.User;
import com.soap.app.mapper.UserDao;
import com.soap.app.service.impl.TextServiceA;
import com.soap.app.service.impl.TextServiceB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestAutowired implements TestAutowiredIn{

    @Autowired
    private UserDao userDao;

    @Autowired
    private TextServiceA textServiceA;
    @Autowired
    private TextServiceB textServiceB;

    public void method1(){
        User u = new User();
        u.setName("wuyu");
        u.setPassword("881019");
        textServiceA.testMethod("aa");
        textServiceB.testMethod("bb");
        userDao.addUser(u);
    }
}
