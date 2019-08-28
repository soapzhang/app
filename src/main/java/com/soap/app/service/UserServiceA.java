package com.soap.app.service;

import com.soap.app.entity.User;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface UserServiceA {
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    void saveUser (User u) ;
}
