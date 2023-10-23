package com.example.yu.framework.helper;

import com.example.yu.framework.annotation.YuAspect;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * Gets all the classes that the @YuAspect section wants to modify
 */
public final class AopHelper {

    private static Set<Class<?>> createTargetClassSet(YuAspect yuAspect) throws Exception {

        Set<Class<?>> targetClassSet = new HashSet<>();
        Class<? extends Annotation> annotation = yuAspect.value();
        if (annotation != null) {
            Set<Class<?>> classSetByAnnotation = ClassHelper.getClassSetByAnnotation(annotation);
            targetClassSet.addAll(classSetByAnnotation);
        }
        return targetClassSet;
    }
}
