package com.lihang.selfmvvm.morefunction.launchstater.utils;

import android.util.Log;

/**
 * Created by leo
 * on 2020/4/29.
 */
public class DispatcherLog {
    private static boolean sDebug = true;

    public static void i(String msg) {
        if (!sDebug) {
            return;
        }
        Log.i("task",msg);
    }

    public static boolean isDebug() {
        return sDebug;
    }

    public static void setDebug(boolean debug) {
        sDebug = debug;
    }
}
