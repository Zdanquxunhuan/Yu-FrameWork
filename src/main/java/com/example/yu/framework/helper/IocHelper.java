package com.example.yu.framework.helper;

import com.example.yu.framework.annotation.YuAutowired;
import com.example.yu.framework.util.ArrayUtil;
import com.example.yu.framework.util.CollectionUtil;
import com.example.yu.framework.util.ReflectionUtil;
import org.apache.commons.collections4.CollectionUtils;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * IOC container initialization（At this time, the objects in the IOC container are all singletons）
 *
 * 通过 BeanHelper ，获取所有的 Bean 类与 Bean 实例之间的映射关系，然后进行遍历
 * 如果属性对应的class对象上有 @YuAutowired注释的话，就从 BeanHelper 获取对应的 Bean 赋值
 */
public final class IocHelper {

    static {
        Map<Class<?>, Object> beanMaps = BeanHelper.getBeanMap();
        if (CollectionUtil.isNotEmpty(beanMaps)) {

            for (Map.Entry<Class<?>, Object> beanEntry : beanMaps.entrySet()) {

                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();
                Field[] beanFields = beanClass.getDeclaredFields();
                if (ArrayUtil.isNotEmpty(beanFields)) {
                    for (Field beanField : beanFields) {
                        if (beanField.isAnnotationPresent(YuAutowired.class)) {
                            Class<?> beanFieldClass = beanField.getType();
                            Object beanFieldInstance = beanMaps.get(beanFieldClass);
                            if (beanFieldInstance != null)
                                ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
                        }
                    }
                }
            }
        }
    }
}
