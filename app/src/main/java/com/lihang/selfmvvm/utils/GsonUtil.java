package com.lihang.selfmvvm.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

public final class GsonUtil {

    private GsonUtil() {
    }

    private static Gson getGsonObject() {
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson;
    }

    /**
     * 对象转Gson字符串
     *
     * @param object
     * @return
     */
    public static <T extends Object> String ser(T object) {
        Gson gson = getGsonObject();
        return gson.toJson(object);
    }

    /**
     * Gson字符串转可序列化对象
     *
     * @param object
     * @param clazz
     * @return
     */
    public static <T extends Object> T deser(String object, Class<T> clazz) {
        Gson gson = getGsonObject();

        T result = null;
        try {
            result = gson.fromJson(object, clazz);
        } catch (Exception e) {
            result = null;
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Gson字符串转可序列化对象
     *
     * @param object
     * @return
     */
    public static <T extends Object> T deser(String object, Type type) {
        Gson gson = getGsonObject();

        T result = null;
        try {
            result = gson.fromJson(object, type);
        } catch (Exception e) {
            result = null;
            e.printStackTrace();
        }

        return result;
    }

    public static <T extends Object> T deserBequiet(String object, Class<T> clazz) {
        Gson gson = getGsonObject();

        T result;
        try {
            result = gson.fromJson(object, clazz);
        } catch (Exception e) {
            result = null;
        }

        return result;
    }

    public static <T> T Json2Result(Class<T> class1, String s) {

        T result;
        try {
            result = new Gson().fromJson(s, class1);
        } catch (Exception e) {
            result = null;
        }

        return result;
    }


}
