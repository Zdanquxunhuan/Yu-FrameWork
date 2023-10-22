package com.example.yu.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface YuRequestMapping {

    /**
     * Request method
     */
    String method();

    /**
     * Request path
     */
    String value();
}
