package com.example.yu.framework.aspect;

import com.example.yu.framework.annotation.YuAspect;
import com.example.yu.framework.annotation.YuController;
import com.example.yu.framework.proxy.BaseAspectProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * Intercept all methods that have YuController marked
 */
@YuAspect(YuController.class)
public class ControllerAspect extends BaseAspectProxy {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAspect.class);

    private long begin;

    @Override
    public void before(Class<?> targetClass, Method method, Object[] params) throws Throwable {
        LOGGER.error("-------------------begin-----------------------");
        LOGGER.debug(String.format("class: %s", targetClass.getName()));
        LOGGER.debug(String.format("method: %s", method.getName()));
        begin = System.currentTimeMillis();
    }

    @Override
    public void after(Class<?> targetClass, Method method, Object[] params) throws Throwable {
        LOGGER.debug(String.format("time: %dms", System.currentTimeMillis() - begin));
        LOGGER.debug("----------- end -----------");
    }


}
