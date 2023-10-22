package com.example.yu.framework.helper;

import com.example.yu.framework.annotation.YuComponent;
import com.example.yu.framework.annotation.YuConfiguration;
import com.example.yu.framework.annotation.YuController;
import com.example.yu.framework.annotation.YuService;
import com.example.yu.framework.util.ClassUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * Get all YuService classes and YuController classes under an application package name.
 * Understand the objects generated by classes with YuController annotations and YuService annotations
 * as beans managed by the Yu-FrameWork framework
 */
public final class ClassHelper {

    private static final Set<Class<?>> CLASS_SET;

    static {
        String basePackage = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    /**
     * Get all classes under the application package name
     */
    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    /**
     * Get all YuService classes under the application package name
     */
    public static Set<Class<?>> getYuServiceClassSet() {
        Set<Class<?>> yuServices = new HashSet<>();
        for (Class<?> clazz : CLASS_SET) {
            if (clazz.isAnnotationPresent(YuService.class))
                yuServices.add(clazz);
        }
        return yuServices;
    }

    /**
     * Get all YuController classes under the application package name
     */
    public static Set<Class<?>> getYuControllerClassSet() {
        Set<Class<?>> yuControllers = new HashSet<>();
        for (Class<?> clazz : CLASS_SET) {
            if (clazz.isAnnotationPresent(YuController.class))
                yuControllers.add(clazz);
        }
        return yuControllers;
    }

    /**
     * Get all YuComponent classes under the application package name
     */
    public static Set<Class<?>> getYuComponentClassSet(){
        Set<Class<?>> yuComponents = new HashSet<>();
        for (Class<?> clazz : CLASS_SET) {
            if (clazz.isAnnotationPresent(YuComponent.class))
                yuComponents.add(clazz);
        }
        return yuComponents;
    }

    /**
     * Get all YuConfiguration classes under the application package name
     */
    public static Set<Class<?>> getYuConfigurationClassSet(){
        Set<Class<?>> yuConfigurations = new HashSet<>();
        for (Class<?> clazz : CLASS_SET) {
            if (clazz.isAnnotationPresent(YuConfiguration.class))
                yuConfigurations.add(clazz);
        }
        return yuConfigurations;
    }

    /**
     * Get all beans under the basic application package
     */
    public static Set<Class<?>> getBeanClassSet() {
        Set<Class<?>> allBeans = new HashSet<>();
        allBeans.addAll(getYuServiceClassSet());
        allBeans.addAll(getYuControllerClassSet());
        allBeans.addAll(getYuComponentClassSet());
        allBeans.addAll(getYuConfigurationClassSet());
        return allBeans;
    }
}
