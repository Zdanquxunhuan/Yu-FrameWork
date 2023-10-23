package com.example.yu.framework.helper;


import com.example.yu.framework.util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Bean container
 */
public final class BeanHelper {

    private final static Map<Class<?>, Object> BEAN_MAP = new HashMap<>();

    static {
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        for (Class<?> aClass : beanClassSet) {
            Object newInstance = ReflectionUtil.newInstance(aClass);
            BEAN_MAP.put(aClass, newInstance);
        }
    }

    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    public static void setBean(Class<?> clazz, Object obj) {
        BEAN_MAP.put(clazz, obj);
    }

    public static <T> T getBean(Class<T> clazz) {

        if (!BEAN_MAP.containsKey(clazz))
            throw new RuntimeException("can not find bean by class:" + clazz);

        return (T) BEAN_MAP.get(clazz);
    }
}
