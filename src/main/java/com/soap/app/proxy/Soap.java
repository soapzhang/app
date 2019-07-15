package com.soap.app.proxy;

/**
 * cglib动态代理测试类
 */
public class Soap {
    public boolean eat(String meat){
        System.out.println("我要吃"+meat);
        return true;
    }
}
