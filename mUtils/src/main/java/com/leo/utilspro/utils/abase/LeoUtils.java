package com.leo.utilspro.utils.abase;

import android.content.Context;

/**
 * Created by leo
 * on 2020/9/22.
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
