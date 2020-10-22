package com.lihang.selfmvvm.utils;

import android.content.Context;

import com.leo.utilspro.utils.ActivitysBuilder;
import com.lihang.selfmvvm.MyApplication;
import com.lihang.selfmvvm.ui.demo.login.LoginActivity;

/**
 * Created by leo
 * on 2020/10/21.
 */
public class AppUtils {
    //这个是直接跳转到登录界面
    public static boolean isLogin(Context context) {
        if (MyApplication.getLoginUser() == null) {
            ActivitysBuilder.build(context, LoginActivity.class)
                    .startActivity();
            return false;
        } else {
            return true;
        }
    }

    public static boolean isLogin() {
        if (MyApplication.getLoginUser() == null) {
            return false;
        } else {
            return true;
        }
    }
}
