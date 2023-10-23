package com.example.yu.framework.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public abstract class BaseAspectProxy implements Proxy {

    private static final Logger logger = LoggerFactory.getLogger(BaseAspectProxy.class);

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {

        Object result = null;

        Class<?> targetClass = proxyChain.getNode().getTargetClass();
        Method method = proxyChain.getNode().getTargetMethod();
        Object[] params = proxyChain.getNode().getMethodParams();

        begin();
        try {

            if (intercept(targetClass, method, params)) {

                before(targetClass, method, params);
                result = proxyChain.doProxyChain();
                after(targetClass, method, params);

            } else {
                result = proxyChain.doProxyChain();
            }

        } catch (Exception e) {
            logger.error("proxy failure :{}", e);
            error(targetClass, method, params, e);
            throw e;
        } finally {
            end();
        }

        return result;
    }

    private void error(Class<?> targetClass, Method method, Object[] params, Exception e) {

    }

    private void after(Class<?> targetClass, Method method, Object[] params) throws Throwable {

    }

    private void before(Class<?> targetClass, Method method, Object[] params) throws Throwable {

    }

    private boolean intercept(Class<?> targetClass, Method method, Object[] params) throws Throwable {
        return true;
    }

    private void end() {
    }

    private void begin() {
    }
}
