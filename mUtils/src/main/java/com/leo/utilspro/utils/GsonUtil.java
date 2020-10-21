package com.leo.utilspro.utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

/**
 * Created by leo
 * on 2020/10/19.
 */
public class GsonUtil {
    private GsonUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
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



    /**
     * 快速读取本地json文件
     * * */
    public String getJson(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


}
