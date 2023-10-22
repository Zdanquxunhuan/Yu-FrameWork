package com.example.yu.framework.init;

import com.example.yu.framework.helper.BeanHelper;
import com.example.yu.framework.helper.ClassHelper;
import com.example.yu.framework.helper.ControllerHelper;
import com.example.yu.framework.helper.IocHelper;
import com.example.yu.framework.util.ClassUtil;

/**
 * You can directly call the init method of HelperLoader to load these Helper classes.
 * In fact, when we access the class for the first time, its static block will be loaded.
 * This is just to make the loading more focused.
 */
public final class ProgramInit {

    public static void doInit() {
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                ControllerHelper.class,
                IocHelper.class
        };
        for (Class<?> aClass : classList) {
            ClassUtil.loadClass(aClass.getName(), false);
        }
    }
}
