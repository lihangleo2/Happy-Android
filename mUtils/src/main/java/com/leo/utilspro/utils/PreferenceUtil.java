package com.leo.utilspro.utils;

import android.content.SharedPreferences;

import com.leo.utilspro.utils.abase.LeoUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by leo
 * on 2020/10/19.
 */
public class PreferenceUtil {
    public static final String FILE_NAME = "leo_pro";

    private PreferenceUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 这里是对基本数据类型进行的操作
     */
    /*
     * 这里是保存基本数据类型 -- 表名是上面设置的FILE_NAME
     * */
    public static void put(String key, Object object) {

        SharedPreferences sp = LeoUtils.getApplication().getSharedPreferences(FILE_NAME,
                LeoUtils.getApplication().MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        editor.commit();
    }

    /*
     * 这里是根据key，获取数据。表名是 -- FILE_NAME
     * 第二个参数是  默认值
     * */
    public static Object get(String key, Object defaultObject) {
        SharedPreferences sp = LeoUtils.getApplication().getSharedPreferences(FILE_NAME,
                LeoUtils.getApplication().MODE_PRIVATE);

        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }


    /*
     * 根据某个key值获取数据  表名 -- FILE_NAME
     * */
    public static void remove(String key) {
        SharedPreferences sp = LeoUtils.getApplication().getSharedPreferences(FILE_NAME,
                LeoUtils.getApplication().MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.commit();
    }

    /*
     * 清楚 表名 -- FILE_NAME 里所有的数据
     * */
    public static void clear() {
        SharedPreferences sp = LeoUtils.getApplication().getSharedPreferences(FILE_NAME,
                LeoUtils.getApplication().MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    /*
     * 判断当前key值 是否存在于  表名--FILE_NAME 表里
     * */
    public static boolean contains(String key) {
        SharedPreferences sp = LeoUtils.getApplication().getSharedPreferences(FILE_NAME,
                LeoUtils.getApplication().MODE_PRIVATE);
        return sp.contains(key);
    }


    /*
     * 返回表名 -- FILE_NAME里所有的数据，以Map键值对的方式
     * */
    public static Map<String, ?> getAll() {
        SharedPreferences sp = LeoUtils.getApplication().getSharedPreferences(FILE_NAME,
                LeoUtils.getApplication().MODE_PRIVATE);
        return sp.getAll();
    }


    /**
     * 以下是保存类的方式，跟上面的FILE_NAME表不在一个表里
     */
    /*
     * 保存类，当前SharedPreferences以 class类名被表名
     * */
    public static <T extends Serializable> boolean putByClass(String key, T entity) {
        if (entity == null) {
            return false;
        }
        String prefFileName = entity.getClass().getName();
        SharedPreferences sp = LeoUtils.getApplication().getSharedPreferences(prefFileName, 0);
        SharedPreferences.Editor et = sp.edit();
        String json = GsonUtil.ser(entity);
        et.putString(key, json);
        return et.commit();
    }


    /*
     * 获取某个以class 为表名的。所有class 对象
     * */
    public static <T extends Serializable> List<T> getAllByClass(Class<T> clazz) {
        String prefFileName = clazz.getName();
        SharedPreferences sp = LeoUtils.getApplication().getSharedPreferences(prefFileName, 0);
        Map<String, String> values = (Map<String, String>) sp.getAll();
        List<T> results = new ArrayList<T>();
        if (values == null || values.isEmpty())
            return results;
        Collection<String> colles = values.values();
        for (String json : colles) {
            results.add(GsonUtil.deser(json, clazz));
        }
        return results;
    }

    /*
     * 获取以类名为表名的，某个key值上的value
     * 第二个参数是，类名class,也就是当前的表名
     * */
    public static <T extends Serializable> T getByClass(String key, Class<T> clazz) {
        String prefFileName = clazz.getName();
        SharedPreferences sp = LeoUtils.getApplication().getSharedPreferences(prefFileName, 0);

        String json = sp.getString(key, null);
        if (json == null) {
            return null;
        }
        return GsonUtil.deser(json, clazz);
    }

    /*
     * 在以类名为表名的表上，移除key值
     * 第二个参数是，类名class,也就是当前的表名
     * */
    public static <T extends Serializable> void removeByClass(String key, Class<T> clazz) {
        String prefFileName = clazz.getName();
        SharedPreferences sp = LeoUtils.getApplication().getSharedPreferences(prefFileName, 0);
        if (sp.contains(key)) {
            sp.edit().remove(key).commit();
        }
    }

    /*
     * 移除 某个以类名为表名上的所有的值
     * */
    public static <T extends Serializable> void clearByClass(Class<T> clazz) {
        String prefFileName = clazz.getName();
        SharedPreferences sp = LeoUtils.getApplication().getSharedPreferences(prefFileName, 0);
        sp.edit().clear().commit();
    }
}
