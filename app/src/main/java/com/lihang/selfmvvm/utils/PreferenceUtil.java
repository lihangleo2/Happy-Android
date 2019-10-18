package com.lihang.selfmvvm.utils;

import android.content.Context;
import android.content.SharedPreferences;


import com.lihang.selfmvvm.MyApplication;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;


public final class PreferenceUtil {

    private static Context context = MyApplication.getContext();
    public static final String FILE_NAME = "leo_pro";

    private PreferenceUtil() {

    }


    public static <T extends Serializable> boolean save(T entity, String key) {
        if (entity == null) {
            return false;
        }
        String prefFileName = entity.getClass().getName();
        SharedPreferences sp = context.getSharedPreferences(prefFileName, 0);
        SharedPreferences.Editor et = sp.edit();
        String json = GsonUtil.ser(entity);
        et.putString(key, json);
        return et.commit();
    }

    public static <T extends Serializable> List<T> findAll(Class<T> clazz) {
        String prefFileName = clazz.getName();
        SharedPreferences sp = context.getSharedPreferences(prefFileName, 0);
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

    public static <T extends Serializable> T find(String key, Class<T> clazz) {
        String prefFileName = clazz.getName();
        SharedPreferences sp = context.getSharedPreferences(prefFileName, 0);

        String json = sp.getString(key, null);
        if (json == null) {
            return null;
        }
        return GsonUtil.deser(json, clazz);
    }

    public static <T extends Serializable> void delete(String key, Class<T> clazz) {
        String prefFileName = clazz.getName();
        SharedPreferences sp = context.getSharedPreferences(prefFileName, 0);
        if (sp.contains(key)) {
            sp.edit().remove(key).commit();
        }
    }

    public static <T extends Serializable> void deleteAll(Class<T> clazz) {
        String prefFileName = clazz.getName();
        SharedPreferences sp = context.getSharedPreferences(prefFileName, 0);
        sp.edit().clear().commit();
    }


    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param key
     * @param object
     */
    public static void put(String key, Object object) {

        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
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

        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object get(String key, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);

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

    /**
     * 移除某个key值已经对应的值
     *
     * @param key
     */
    public static void remove(String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     */
    public static void clear() {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param key
     * @return
     */
    public static boolean contains(String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @return
     */
    public static Map<String, ?> getAll() {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.getAll();
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author zhy
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            editor.commit();
        }
    }

}
