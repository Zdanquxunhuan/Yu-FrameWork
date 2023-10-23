package com.example.yu.framework.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface YuAspect {

    Class<? extends Annotation> value();
}
