package com.lihang.selfmvvm.aop;

import android.util.Log;

import com.lihang.selfmvvm.MyApplication;
import com.lihang.selfmvvm.base.BaseActivity;
import com.lihang.selfmvvm.base.BaseFragment;
import com.lihang.selfmvvm.ui.login.LoginActivity;
import com.lihang.selfmvvm.utils.ActivityUtils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * Created by leo
 * on 2020/4/28.
 */
@Aspect
public class AspectHelper {
    @Around("execution(@com.lihang.selfmvvm.aop.NeedLogin * *(..))")
    public void isLoginOn(ProceedingJoinPoint joinPoint) throws Throwable {
        if (MyApplication.getLoginUser() != null) {
            joinPoint.proceed();
        } else {
            if (joinPoint.getThis() instanceof BaseActivity) {
                ActivityUtils.startActivity((BaseActivity) joinPoint.getThis(), LoginActivity.class);
            } else if (joinPoint.getThis() instanceof BaseFragment) {
                ActivityUtils.startActivity(((BaseFragment) joinPoint.getThis()).getActivity(), LoginActivity.class);
            }
        }
    }
}
