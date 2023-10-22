package com.example.yu.framework.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Method;

/**
 * Encapsulate classes and methods marked with @YuAutowired
 */
@Data
@AllArgsConstructor
public class Handler {

    private Class<?> controllerClass;
    private Method yuAutowiredMethod;
}
