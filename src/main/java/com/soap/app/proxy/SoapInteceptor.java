package com.soap.app.proxy;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * cglib动态代理的实现
 */
public class SoapInteceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("预处理");
        Object object = methodProxy.invokeSuper(o,objects);
        System.out.println("处理结束");
        return object;
    }
}
