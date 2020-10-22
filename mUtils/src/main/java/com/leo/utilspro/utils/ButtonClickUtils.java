package com.leo.utilspro.utils;

/**
 * Created by leo
 * on 2019/11/15.
 * 防止快速点击
 */
public class ButtonClickUtils {

    private ButtonClickUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 500;
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();

        if ((curClickTime - lastClickTime) < MIN_CLICK_DELAY_TIME) {
            flag = true;
        } else {
            lastClickTime = curClickTime;
        }
        return flag;
    }

}
