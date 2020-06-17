package com.lihang.selfmvvm.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.lihang.selfmvvm.MyApplication;

/**
 * Created by leo
 * on 2019/11/13.
 * activity 的工具类
 */

public class ActivityUtils {
    private ActivityUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void startActivity(Context context, Class<? extends Activity> clz) {
        Intent intent = new Intent(context, clz);
        context.startActivity(intent);
    }

    public static void finishWithAnim(Activity activity, int enterAnim, int exitAnim) {
        activity.finish();
        activity.overridePendingTransition(enterAnim, exitAnim);
    }

    public static Intent build(Context context, Class<? extends Activity> clz){
        Intent intent = new Intent(context, clz);
        return intent;
    }

    public static void startHome(Context context){
Intent home = new Intent(Intent.ACTION_MAIN);
        home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        home.addCategory(Intent.CATEGORY_HOME);
        context.startActivity(home);
    }
}
