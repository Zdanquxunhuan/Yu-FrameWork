package com.example.yu.framework.annotation;

import java.lang.annotation.*;

/**
 * 只能大范围（注解）进行切面
 * TODO 缩小切面范围
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface YuAspect {

    Class<? extends Annotation> value();
}
