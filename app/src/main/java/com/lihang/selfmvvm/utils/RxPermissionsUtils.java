package com.lihang.selfmvvm.utils;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.leo.utilspro.utils.ActivitysBuilder;
import com.lihang.selfmvvm.MyApplication;
import com.lihang.selfmvvm.ui.demo.login.LoginActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;

/**
 * Created by leo
 * on 2020/10/21.
 * 当前环境下app所用到的工具类
 */
public class RxPermissionsUtils {
    public static RxPermissions with(FragmentActivity activity) {
        return new RxPermissions(activity);
    }

    public static RxPermissions with( Fragment fragment) {
        return new RxPermissions(fragment);
    }
}
