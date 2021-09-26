package com.lihang.selfmvvm.utils;

import android.content.Context;

import com.leo.utilspro.utils.ActivitysBuilder;
import com.lihang.selfmvvm.MyApplication;
import com.lihang.selfmvvm.ui.demo.login.LoginActivity;

/**
 * Created by leo
 * on 2020/10/21.
 * 当前环境下app所用到的工具类
 */
public class AppUtils {
    //判断当前app是否登录，没有登录直接跳转到LoginActivity页面
    public static boolean isLogin(Context context) {
        if (MyApplication.getLoginUser() == null) {
            ActivitysBuilder.build(context, LoginActivity.class)
                    .startActivity();
            return false;
        } else {
            return true;
        }
    }

    //判断当前app是否登录
    public static boolean isLogin() {
        if (MyApplication.getLoginUser() == null) {
            return false;
        } else {
            return true;
        }
    }
}
