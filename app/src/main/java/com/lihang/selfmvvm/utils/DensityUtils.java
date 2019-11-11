package com.lihang.selfmvvm.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.util.TypedValue;
import android.view.WindowManager;

import com.lihang.selfmvvm.MyApplication;


/**
 * Created by leo
 * on 2019/11/11.
 * 常用单位换算，获取屏幕宽高，获取屏幕状态栏高度
 */
public class DensityUtils {

    private DensityUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /*
     * dp 转 px
     * */
    public static int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, MyApplication.getContext().getResources().getDisplayMetrics());
    }

    /*
     * px 转 dp
     * */
    public static float px2dp(float pxVal) {
        final float scale = MyApplication.getContext().getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /*
     * sp 转 px
     * */
    public static int sp2px(float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, MyApplication.getContext().getResources().getDisplayMetrics());
    }


    /*
     * px 转 sp
     * */
    public static float px2sp(float pxVal) {
        return (pxVal / MyApplication.getContext().getResources().getDisplayMetrics().scaledDensity);
    }


    /*
     * 获取屏幕宽度
     * */
    public static int getScreenWidth() {
        WindowManager wm = (WindowManager) MyApplication.getContext().getSystemService(Context.WINDOW_SERVICE);
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
        WindowManager wm = (WindowManager) MyApplication.getContext().getSystemService(Context.WINDOW_SERVICE);
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
        Resources resources = MyApplication.getContext().getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }
}
