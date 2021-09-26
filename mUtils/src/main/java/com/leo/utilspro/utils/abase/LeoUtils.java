package com.leo.utilspro.utils.abase;

import android.content.Context;

/**
 * Created by leo
 * on 2020/9/22.
 * Leo工具类，必须实现注册下。目的是方便其他工具不需要获取Context。
 *
 * LeoUtils.initContext(this);
 */
public class LeoUtils {
    private static Context mContext;

    private LeoUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void initContext(Context context){
        mContext = context;
    }

    public static Context getApplication() {
        if (mContext!=null){
            return mContext;
        }else {
            throw new UnsupportedOperationException("context 未初始化");
        }
    }
}
