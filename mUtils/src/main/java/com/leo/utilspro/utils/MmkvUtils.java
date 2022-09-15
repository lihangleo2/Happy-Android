package com.leo.utilspro.utils;

import com.leo.utilspro.utils.abase.LeoUtils;
import com.tencent.mmkv.MMKV;

import java.io.Serializable;

/**
 * @Author leo
 * @Date 2022/9/14
 */
public class MmkvUtils {

    private MmkvUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void init() {
        MMKV.initialize(LeoUtils.getApplication());
    }


    /*
    默认表名
    put值
     */
    public static void put(String key, Object object) {
        MMKV mmkv = MMKV.defaultMMKV();
        if (object instanceof String) {
            mmkv.encode(key, (String) object);
        } else if (object instanceof Integer) {
            mmkv.encode(key, (Integer) object);
        } else if (object instanceof Boolean) {
            mmkv.encode(key, (Boolean) object);
        } else if (object instanceof Float) {
            mmkv.encode(key, (Float) object);
        } else if (object instanceof Long) {
            mmkv.encode(key, (Long) object);
        } else {
            mmkv.encode(key, object.toString());
        }
    }

    /*
    默认表名
    获取值
     */
    public static Object get(String key, Object defaultObject) {
        MMKV mmkv = MMKV.defaultMMKV();
        if (defaultObject instanceof String) {
            return mmkv.decodeString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return mmkv.decodeInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return mmkv.decodeBool(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return mmkv.decodeFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return mmkv.decodeLong(key, (Long) defaultObject);
        }
        return null;
    }


    /*
    默认表名
    移除值
     */
    public static void remove(String key) {
        MMKV mmkv = MMKV.defaultMMKV();
        mmkv.removeValueForKey(key);
    }


    /*
    默认表名
    判断当前值是否存在
     */
    public static boolean contains(String key) {
        MMKV mmkv = MMKV.defaultMMKV();
        return mmkv.contains(key);
    }


    /*
    默认表明
    用来保存类
     */
    public static <T extends Serializable> boolean putByClass(String key, T entity) {
        if (entity == null) {
            return false;
        }
        MMKV mmkv = MMKV.defaultMMKV();
        String json = GsonUtil.ser(entity);
        return mmkv.encode(key, json);
    }

    /*
    默认表明
    用来保存类
    1、移除和查询同普通数据类型
     */
    public static <T extends Serializable> T getByClass(String key, Class<T> clazz) {
        MMKV mmkv = MMKV.defaultMMKV();
        String json = mmkv.decodeString(key,null);
        if (json == null) {
            return null;
        }
        return GsonUtil.deser(json, clazz);
    }

    /*
    如果不想用默认表明，也可以用如下方式，自定义个
    MMKV kv = MMKV.mmkvWithID("MyID");
    */


}
