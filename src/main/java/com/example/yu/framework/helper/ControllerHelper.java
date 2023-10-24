package com.example.yu.framework.helper;

import com.example.yu.framework.annotation.YuRequestMapping;
import com.example.yu.framework.bean.Handler;
import com.example.yu.framework.bean.Request;
import com.example.yu.framework.util.ArrayUtil;
import com.example.yu.framework.util.CollectionUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class ControllerHelper {

    /**
     * Used to store the mapping relationship between requests and processors
     */
    private static final Map<Request, Handler> REQUEST_HANDLER = new HashMap<>();

    static {
        Set<Class<?>> yuControllerClassSets = ClassHelper.getYuControllerClassSet();
        if (CollectionUtil.isNotEmpty(yuControllerClassSets)) {
            for (Class<?> yuControllerClass : yuControllerClassSets) {
                Method[] methods = yuControllerClass.getDeclaredMethods();
                if (ArrayUtil.isNotEmpty(methods)) {
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(YuRequestMapping.class)) {
                            YuRequestMapping yuRequestMapping = method.getAnnotation(YuRequestMapping.class);
                            String requestMethod = yuRequestMapping.method();
                            String requestPath = yuRequestMapping.value();
                            Request request = new Request(requestMethod, requestPath);
                            Handler handler = new Handler(yuControllerClass, method);
                            REQUEST_HANDLER.put(request, handler);
                        }
                    }
                }
            }
        }
    }

    /**
     * Get the corresponding processor
     * @param requestMethod Request method
     * @param requestPath Request path
     * @return processor
     */
    public static Handler getHandler(String requestMethod, String requestPath) {
        Request request = new Request(requestMethod, requestPath);
        return REQUEST_HANDLER.get(request);
    }
}
