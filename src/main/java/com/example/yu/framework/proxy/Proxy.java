package com.example.yu.framework.proxy;

/**
 * Execute the chain proxy
 */
public interface Proxy {

    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
