package com.github.StormWyrm.library.rx.rxbus;

import android.util.Log;

import com.github.StormWyrm.library.BuildConfig;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class Utils {
    private Utils() {
    }

    /**
     * Require the objects are not null.
     *
     * @param objects the Object
     * @throws NullPointerException if any object is null in objects
     */
    public static void requireNonNull(final Object... objects) {
        if (objects == null) {
            throw new NullPointerException();
        }
        for (Object object : objects) {
            if (object == null)
                throw new NullPointerException();
        }
    }

    public static Class getClassFromObject(Object obj) {
        if (obj == null) return null;
        Class clazz = obj.getClass();
        //如果是匿名类或者合成类
        if (clazz.isAnonymousClass() || clazz.isSynthetic()) {
            Type[] genericInterfaces = clazz.getGenericInterfaces();
            String className;
            if (genericInterfaces.length == 1) {
                Type type = genericInterfaces[0];
                if (type instanceof ParameterizedType) {
                    type = ((ParameterizedType) type).getRawType();
                }
                className = type.toString();
            } else {
                Type type = clazz.getGenericSuperclass();
                while (type instanceof ParameterizedType) {
                    type = ((ParameterizedType) type).getRawType();
                }
                className = type.toString();
            }
            try {
                return Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return clazz;
    }

    public static <T> Class<T> getTypeClassFromPara(Rxbus.Callback<T> callback) {
        if (callback == null) return null;
        Type[] genericInterfaces = callback.getClass().getGenericInterfaces();
        Type type;
        if (genericInterfaces.length == 1) {
            type = genericInterfaces[0];
        } else {
            type = callback.getClass().getGenericSuperclass();
        }
        type = ((ParameterizedType) type).getActualTypeArguments()[0];
        while (type instanceof ParameterizedType) {
            type = ((ParameterizedType) type).getRawType();
        }
        String className = type.toString();
        if (className.startsWith("class ")) {
            className = className.substring(6);
        } else if (className.startsWith("interface ")) {
            className = className.substring(10);
        }
        try {
            //noinspection unchecked
            return (Class<T>) Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void logD(String msg) {
        if (BuildConfig.DEBUG) {
            Log.d("RxBus", "lodD: " + msg);
        }
    }
}
