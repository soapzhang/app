package com.soap.app.service.impl;

import com.soap.app.entity.User;
import com.soap.app.mapper.UserDao;
import com.soap.app.service.UserServiceA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 事务的传播行为：A方法调用B方法
 * PROPAGATION_REQUIRED：A无事务，B新事务影响自己，A有事务，B不起事务，A异常，AB回滚，B异常，AB回滚，A捕获B的异常，会抛出UnexpectedRollbackException异常
 * PROPAGATION_SUPPORTS：A无事务，B也无事务，A有事务，A异常，AB回滚，B异常，AB回滚，A捕获B的异常，会抛出UnexpectedRollbackException异常
 * PROPAGATION_MANDATORY：A无事务，B抛出异常，A之前的数据操作不影响，A有事务，A异常，AB回滚，B异常，AB回滚，A捕获B的异常，会抛出UnexpectedRollbackException异常
 * PROPAGATION_NESTED：A无事务，B事务只影响自己，A有事务，A异常，AB回滚，B异常，A捕获？不会滚：不回滚，B都回滚
 * PROPAGATION_NEVER：A有事务，B直接异常
 * PROPAGATION_REQUIRES_NEW ：A无事务，B事务只影响自己，A有事务，A异常，B不受影响，B异常，A捕获？不会滚：回滚
 * PROPAGATION_NOT_SUPPORTED ：A方法有事务，那么B异常，A捕获异常，不会滚，A不捕获，A回滚，B异常前的操作有效
 *
 * @Transactional 配置在接口方法和实现类方法上，作用效果是一样的，那建议还是在类方法上，一码归一码看得清
 * 配置在接口或者类上，那么所有的public方法都会起作用。
 */
@Service
public class UserServiceImplA implements UserServiceA {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserServiceImplB userServiceImplB;

    @Override
    public void saveUser(User u) {
        /**
         * A无事务更新数据库，并调用B方法,B方法抛出异常，A不捕获异常
         */
            userDao.addUser(u);
//            int a=1/0;
    }

}
