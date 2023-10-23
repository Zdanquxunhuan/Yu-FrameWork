package com.example.yu.framework.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

public class ProxyManager {

    public static <T> T createProxy(final Class<?> targetClass, final List<Proxy> proxyList) {
        return (T) Enhancer.create(targetClass, new MethodInterceptor() {
            @Override
            public Object intercept(Object targetObject, Method targetMethod,
                                    Object[]methodParams, MethodProxy methodProxy) throws Throwable {
                ProxyChain.Node node  = new ProxyChain.Node(targetClass,targetObject,targetMethod,methodProxy,methodParams);
                return new ProxyChain(node, proxyList).doProxyChain();
            }
        });
    }
}
