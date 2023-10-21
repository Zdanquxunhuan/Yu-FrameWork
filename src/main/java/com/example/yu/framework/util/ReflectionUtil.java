package com.example.yu.framework.util;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Reflection tool class
 */
public class ReflectionUtil {
    private static Logger logger = LoggerFactory.getLogger(ReflectionUtil.class);

    public static Object newInstance(Class<?> clazz) {
        Object obj = null;
        try {
            obj = clazz.newInstance();
        } catch (Exception e) {
            logger.error("new instance failure", e);
            throw new RuntimeException(e);
        }
        return obj;
    }

    public static Object invokeMethod(Object obj, Method method, Object... args) {
        Object result = null;
        try {
            result = method.invoke(obj, args);
        } catch (Exception e) {
            logger.error("invoke method failure", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    public static void setField(Object obj, Field field, Object value) {

        try {
            field.setAccessible(true);
            field.set(obj, value);
        } catch (Exception e) {
            logger.error("set field failure", e);
            throw new RuntimeException(e);
        }
    }

}
