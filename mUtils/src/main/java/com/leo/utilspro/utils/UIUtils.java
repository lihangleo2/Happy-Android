package com.leo.utilspro.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

import com.leo.utilspro.utils.abase.LeoUtils;

import java.lang.reflect.Method;

/**
 * Created by leo
 * on 2020/10/15.
 * 常用单位换算，获取屏幕宽高，获取屏幕状态栏高度
 */
public class UIUtils {

    private UIUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /*
     * dp 转 px
     * */
    public static int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, LeoUtils.getApplication().getResources().getDisplayMetrics());
    }

    /*
     * px 转 dp
     * */
    public static float px2dp(float pxVal) {
        final float scale = LeoUtils.getApplication().getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /*
     * sp 转 px
     * */
    public static int sp2px(float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, LeoUtils.getApplication().getResources().getDisplayMetrics());
    }

    /*
     * px 转 sp
     * */
    public static float px2sp(float pxVal) {
        return (pxVal / LeoUtils.getApplication().getResources().getDisplayMetrics().scaledDensity);
    }

    /*
     * 获取屏幕宽度
     * */
    public static int getScreenWidth() {
        WindowManager wm = (WindowManager) LeoUtils.getApplication().getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) return -1;
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.x;
    }


    /*
     * 获取屏幕高度
     * */
    public static int getScreenHeight() {
        WindowManager wm = (WindowManager) LeoUtils.getApplication().getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) return -1;
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.y;
    }


    /*
     * 获取屏幕状态栏高度
     * */
    public static int getStatusBarHeight() {
        Resources resources = LeoUtils.getApplication().getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }


    /*
    * 通过反射，获取包含虚拟键的整体屏幕高度
    * 有些android手机可以设置全屏或者是虚拟键，如果用getStatusBarHeight获取，往往不是全屏
    * */
    private int getHasVirtualKey(Activity activity) {
        int dpi = 0;
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            dpi = dm.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dpi;
    }

}
