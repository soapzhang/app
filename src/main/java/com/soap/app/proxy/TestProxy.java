package com.soap.app.proxy;

import net.sf.cglib.proxy.Enhancer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * 动态代理的测试类
 */
public class TestProxy {
    public static void main(String[] args) {
        //静态代理
        UserHandlerProxy proxy = new UserHandlerProxy();
        proxy.method("苹果");
        //动态代理
        UserHandler target = new UserHandlerImpl();
        InvocationHandler h = new DynamicProxy(target);
        UserHandler proxy1 = (UserHandler)Proxy.newProxyInstance(UserHandler.class.getClassLoader(), new Class[]{UserHandler.class}, h);
        proxy1.method("桃子");
        //cglib动态代理
        Enhancer enhancer = new Enhancer();//字节码增强器
        enhancer.setSuperclass(Soap.class);//设置被代理类为父类
        enhancer.setCallback(new SoapInteceptor());//设置回调函数
        Soap soap = (Soap) enhancer.create();//创建代理类
        soap.eat("葡萄");
    }
}
