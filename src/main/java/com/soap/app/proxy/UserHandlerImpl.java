package com.soap.app.proxy;

/**
 * 被代理的实例
 */
public class UserHandlerImpl implements UserHandler {
    @Override
    public void method(String apple) {
        System.out.println("eat:"+apple);
    }
}
