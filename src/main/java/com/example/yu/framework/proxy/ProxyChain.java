package com.example.yu.framework.proxy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Accessors(chain = true)
public class ProxyChain {
    private Node node;
    private List<Proxy> proxyList = new ArrayList<>();
    private int proxyIndex = 0;

    public Object doProxyChain() throws Throwable {
        Object methodResult;

        if (proxyIndex < proxyList.size())
            //The Proxy interface implementation provides the corresponding crosscutting logic
            methodResult = proxyList.get(proxyIndex++).doProxy(this);
        else
            //Execute the business logic of the target object
            methodResult = node.methodProxy.invokeSuper(node.targetObject, node.methodParams);

        return methodResult;
    }

    public ProxyChain(Node node, List<Proxy> proxyList) {
        this.node = node;
        this.proxyList = proxyList;
    }

    @Data
    @AllArgsConstructor
    static class Node{
        private final Class<?> targetClass;
        private final Object targetObject;
        private final Method targetMethod;
        private final MethodProxy methodProxy;
        private final Object[] methodParams;

    }
}
