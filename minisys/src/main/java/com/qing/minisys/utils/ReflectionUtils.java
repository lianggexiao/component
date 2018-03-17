package com.qing.minisys.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;

/**
 * @Desc 反射工具类
 */
public class ReflectionUtils {

    /**
     * 获取类中所有方法（不包括私有方法）
     * @param className
     * @return
     * @throws ClassNotFoundException
     */
    public static Method[] getMethods(String className) throws ClassNotFoundException {
        Class<?> clazz = Class.forName(className);
        return clazz.getMethods();
    }

    /**
     * 获取类中所有方法（包括私有方法）
     * @param className 类全名
     * @return
     * @throws ClassNotFoundException
     */
    public static Method[] getAllMethods(String className) throws ClassNotFoundException {
        Class<?> clazz = Class.forName(className);
        return clazz.getDeclaredMethods();
    }

    /**
     * 获取类中指定方法
     * @param clazz
     * @param methodName
     * @return
     * @throws Exception
     */
    public static Method getMethod(Class<?> clazz, String methodName) throws ClassNotFoundException, Exception {

        if(null == clazz) {
            throw new ClassNotFoundException("class '" + clazz + "' not found");
        }

        Method result = null;
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if(method.getName().equals(methodName)) {
                result = method;
                break;
            }
        }

        if(null == result) {
            throw new Exception("method '" + methodName + "' not found in class '" + clazz.getName() + "'");
        }

        return result;
    }

    /**
     * 获取类中指定方法
     * @param className
     * @param methodName
     * @return
     * @throws ClassNotFoundException
     * @throws Exception
     */
    public static Method getMethod(String className, String methodName) throws ClassNotFoundException, Exception {
        Class<?> clazz = Class.forName(className);
        return getMethod(clazz, methodName);
    }

    /**
     * 获取类中所有的公共属性
     * @param className
     * @return
     * @throws ClassNotFoundException
     */
    public static Field[] getFields(String className) throws ClassNotFoundException {
        Class<?> clazz = Class.forName(className);
        return clazz.getFields();
    }

    /**
     * 获取类中所有的属性
     * @param className
     * @return
     * @throws ClassNotFoundException
     */
    public static Field[] getAllFields(String className) throws ClassNotFoundException {
        Class<?> clazz = Class.forName(className);
        return clazz.getDeclaredFields();
    }

    /**
     * 获取类中指定属性，根据属性名获取
     * @param className
     * @param fieldName
     * @return
     * @throws ClassNotFoundException
     * @throws NoSuchFieldException
     */
    public static Field getField(String className, String fieldName) throws ClassNotFoundException, NoSuchFieldException {
        Class<?> clazz = Class.forName(className);
        return clazz.getDeclaredField(fieldName);
    }

    /**
     * 获取方法的所有入参名称，仅适用于JDK8，并且编译的时候带上了-parameter参数</br>
     * 例如：<code>public void update(String username, String password, String address){...}</code></br>
     * 返回的结果是：{"username", "password", "address"}
     * @param method
     * @return
     */
    public static String[] getParameterNames(Method method) {
        Parameter[] parameters = method.getParameters();

        int length = parameters.length;
        String[] paramNames = new String[length];
        for (int i = 0; i < length; i++) {
            paramNames[i] = parameters[i].getName();
        }

        return paramNames;
    }

    /**
     * 给属性赋值
     * @param field
     * @param target 属性所属类的对象
     * @param value 属性值
     */
    public static void setField(Field field, Object target, Object value) {
        org.springframework.util.ReflectionUtils.setField(field, target, value);
    }

    /**
     * 获取属性值
     * @param field
     * @param target 属性所属类的对象
     * @return
     */
    public static Object getField(Field field, Object target) {
        return org.springframework.util.ReflectionUtils.getField(field, target);
    }

    /**
     * 方法执行
     * @param method
     * @param target
     * @return
     */
    public static Object invokeMethod(Method method, Object target) {
        return org.springframework.util.ReflectionUtils.invokeMethod(method, target);
    }

    /**
     * 方法执行
     * @param method
     * @param target
     * @param args
     * @return
     */
    public static Object invokeMethod(Method method, Object target, Object... args) {
        return org.springframework.util.ReflectionUtils.invokeMethod(method, target, args);
    }

    /**
     * 处理反射抛出的异常
     * @param ex
     */
    public static void handleReflectionException(Exception ex) {
        org.springframework.util.ReflectionUtils.handleReflectionException(ex);
    }

    /**
     * 处理反射过程中目标异常
     * @param ex
     */
    public static void handleInvocationTargetException(InvocationTargetException ex) {
        org.springframework.util.ReflectionUtils.handleInvocationTargetException(ex);
    }

    /**
     * 重新抛出运行时异常
     * @param ex
     */
    public static void rethrowRuntimeException(Throwable ex) {
        org.springframework.util.ReflectionUtils.rethrowRuntimeException(ex);
    }

    /**
     * 重新抛出异常
     * @param ex
     * @throws Exception
     */
    public static void rethrowException(Throwable ex) throws Exception {
        org.springframework.util.ReflectionUtils.rethrowException(ex);
    }

    /**
     * 获取实例中某个属性的值
     * @param object
     * @param fieldName
     * @return
     */
    public static Object getFieldValue(Object object, String fieldName) {
        Field field = getDeclaredField(object, fieldName);
        if(null == field) {
            throw new IllegalArgumentException("");
        }
        makeAccessible(field);
        Object result = null;
        try {
            result = field.get(object);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * 配置某个属性可读
     * @param field
     */
    public static void makeAccessible(Field field) {
        if((!Modifier.isPublic(field.getModifiers()))
                || (!Modifier.isPublic(field.getDeclaringClass().getModifiers()))) {
            field.setAccessible(true);
        }
    }
    
    /**
     * 获取实例中某个属性对象
     * @param object
     * @param fieldName
     * @return
     */
    public static <T> Field getDeclaredField(Object object, String fieldName) {
        for(Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
