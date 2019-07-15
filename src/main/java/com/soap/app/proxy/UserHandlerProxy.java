package com.soap.app.proxy;

/**
 * 静态代理的示例
 */
public class UserHandlerProxy implements UserHandler {

    private UserHandler userHandler = new UserHandlerImpl();

    @Override
    public void method(String apple) {
        System.out.println("eat之前");
        userHandler.method(apple);
        System.out.println("eat之后");
    }
}
