package com.example.yu.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Load all classes under the basic package name
 */
public class ClassUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtil.class);

    /**
     * 类加载器主要作用是将.class文件加载到JVM中，并将其转换为一个运行时的类。
     * 类加载器负责在运行时查找和加载Java类文件，它支持以下功能：
     * <p>
     * 加载： 类加载器负责从文件系统、JAR文件、网络等地方加载字节码文件，并将其转换为一个类的实例。
     * 连接： 类加载器在加载类的过程中会进行连接，包括验证、准备和解析这些步骤。验证确保加载的类符合Java语言规范，准备阶段为类的静态变量分配内存并设置默认初始值，解析阶段将符号引用解析为直接引用。
     * 初始化： 当类被加载后，类加载器会执行类的初始化操作，包括对静态变量赋予正确的初始值和执行静态代码块。
     * 隔离： 类加载器可以实现类的隔离，不同的类加载器加载的类之间是相互隔离的，这样可以避免类冲突和名称空间污染。
     * 安全性： 类加载器可以实现安全管理策略，确保只有经过授权的类加载器才能加载特定的类。
     *
     * @return
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * Load class
     *
     * @param className
     * @param isInitialized Whether to execute initialization code -
     *                      FALSE : In order to improve the performance of loading classes
     * @return
     */
    public static Class<?> loadClass(String className, boolean isInitialized) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className, isInitialized, getClassLoader());
        } catch (ClassNotFoundException e) {
            LOGGER.error("ClassUtil loadClass error:", e);
            throw new RuntimeException(e);
        }
        return clazz;
    }

    /**
     * Get all classes under the specified package name
     */
    public static Set<Class<?>> getClassSet(String packageName) {
        Set<Class<?>> classSet = new HashSet<>();
        try {
            Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(".", "/"));
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (url != null) {
                    String protocol = url.getProtocol();
                    if (protocol.equals("file")) {
                        String packagePath = url.getPath().replaceAll("20%", " ");
                        addClass(classSet, packagePath, packageName);
                    } else if (protocol.equals("jar")) {
                        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                        if (jarURLConnection != null) {
                            JarFile jarFile = jarURLConnection.getJarFile();
                            if (jarFile != null) {
                                Enumeration<JarEntry> jarEntries = jarFile.entries();
                                while (jarEntries.hasMoreElements()) {
                                    JarEntry jarEntry = jarEntries.nextElement();
                                    String jarEntryName = jarEntry.getName();
                                    if (jarEntryName.endsWith(".class")) {
                                        String className = jarEntryName.substring(0, jarEntryName.lastIndexOf("."))
                                                .replaceAll("/", ".");
                                        doAddClass(classSet, className);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("get class set failure", e);
            throw new RuntimeException(e);
        }
        return classSet;
    }

    private static void addClass(Set<Class<?>> classSet, String packagePath, String packageName) {
        File[] files = new File(packagePath).listFiles(new FileFilter() {
            public boolean accept(File file) {
                return (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory();
            }
        });
        for (File file : files) {
            String fileName = file.getName();
            if (file.isFile()) {
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                if (StringUtil.isNotEmpty(packageName)) {
                    className = packageName + "." + className;
                }
                doAddClass(classSet, className);
            } else {
                String subPackagePath = fileName;
                if (StringUtil.isNotEmpty(packagePath)) {
                    subPackagePath = packagePath + "/" + subPackagePath;
                }
                String subPackageName = fileName;
                if (StringUtil.isNotEmpty(packageName)) {
                    subPackageName = packageName + "." + subPackageName;
                }
                addClass(classSet, subPackagePath, subPackageName);
            }
        }
    }

    private static void doAddClass(Set<Class<?>> classSet, String className) {
        Class<?> cls = loadClass(className, false);
        classSet.add(cls);
    }
}
