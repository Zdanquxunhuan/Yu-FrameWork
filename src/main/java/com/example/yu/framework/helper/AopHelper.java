package com.example.yu.framework.helper;

import com.example.yu.framework.annotation.YuAspect;
import com.example.yu.framework.proxy.BaseAspectProxy;
import com.example.yu.framework.proxy.Proxy;
import com.example.yu.framework.proxy.ProxyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * Gets all the classes that the @YuAspect section wants to modify
 */
public final class AopHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(AopHelper.class);


    /**
     * Initialize the entire AOP framework
     */
    static {
        try {
            Map<Class<?>, Set<Class<?>>> yuAspect_targetClassMap = getYuAspect_TargetClassMap();
            Map<Class<?>, List<Proxy>> target_proxyListMap = getTarget_ProxyListMap(yuAspect_targetClassMap);
            for (Map.Entry<Class<?>, List<Proxy>> entry : target_proxyListMap.entrySet()) {
                Class<?> targetClass = entry.getKey();
                List<Proxy> proxyList = entry.getValue();
                Object proxy = ProxyManager.createProxy(targetClass, proxyList);
                BeanHelper.setBean(targetClass,proxy);
            }

        }catch (Exception e){
            LOGGER.error("aop init fail",e);
        }
    }

    /**
     * Obtain the mapping relationship between the proxy class (aspect class) and its target class set.
     * One proxy class can correspond to one or more target classes.
     *
     * <P>1、Get all aspect classes and traverse them
     * <P>2、If this aspect class contains the YuAspect annotation
     * <P>3、Get all classes corresponding to @YuAspect's value(),
     * that is, all classes modified by this aspect class (target class)
     * <P>4、Put (aspect class, target class) in a Map.
     */
    public static Map<Class<?>, Set<Class<?>>> getYuAspect_TargetClassMap() throws Exception {
        Map<Class<?>, Set<Class<?>>> aspect_TargetClass = new HashMap<>();

        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(BaseAspectProxy.class);
        for (Class<?> proxyClass : proxyClassSet) {
            if (proxyClass.isAnnotationPresent(YuAspect.class)) {
                YuAspect yuAspect = proxyClass.getAnnotation(YuAspect.class);
                Set<Class<?>> aspectTargetClassSet = createAspectTargetClassSet(yuAspect);
                aspect_TargetClass.put(proxyClass, aspectTargetClassSet);
            }
        }
        return aspect_TargetClass;
    }


    /**
     * Mapping relationship between target class and proxy object list
     *
     * <P> Principle example
     * <P>
     * logAspect - PlayController                   PlayController  - logAspect
     *             DanceController                                    AuthAspect
     *
     * AuthAspect - PlayController    ------\      DanceController  - logAspect
     *              DanceController   ------/                         AuthAspect
     *
     * CallBackAspect - PlayService                 PlayService  - CallBackAspect
     *                  DanceService                DanceService - CallBackAspect
     *
     */
    private static Map<Class<?>, List<Proxy>> getTarget_ProxyListMap(Map<Class<?>, Set<Class<?>>> aspect_TargetClass) throws Exception {
        Map<Class<?>, List<Proxy>> target_ProxyList = new HashMap<>();
        for (Map.Entry<Class<?>, Set<Class<?>>> entry : aspect_TargetClass.entrySet()) {

            Class<?> aspect = entry.getKey();
            Set<Class<?>> targetClassSet = entry.getValue();

            for (Class<?> targetClass : targetClassSet) {
                Proxy proxy = (Proxy) aspect.newInstance();

                if(target_ProxyList.containsKey(targetClass)){
                    target_ProxyList.get(targetClass).add(proxy);
                }else {
                    List<Proxy> proxyList=new ArrayList<>();
                    proxyList.add(proxy);
                    target_ProxyList.put(targetClass,proxyList);
                }
            }
        }
        return target_ProxyList;
    }

    /**
     * 获取@YuAspect的value()表示的类型的集合
     *
     * @param yuAspect
     * @return
     * @throws Exception
     */
    private static Set<Class<?>> createAspectTargetClassSet(YuAspect yuAspect) throws Exception {

        Set<Class<?>> targetClassSet = new HashSet<>();
        Class<? extends Annotation> annotation = yuAspect.value();
        if (annotation != null && !annotation.equals(YuAspect.class)) {
            Set<Class<?>> classSetByAnnotation = ClassHelper.getClassSetByAnnotation(annotation);
            targetClassSet.addAll(classSetByAnnotation);
        }
        return targetClassSet;
    }
}
