package com.lihang.selfmvvm.ui.demo.activity;

import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.leo.utilspro.utils.ActivitysBuilder;
import com.leo.utilspro.utils.LogUtils;
import com.lihang.selfmvvm.BuildConfig;
import com.lihang.selfmvvm.R;
import com.lihang.selfmvvm.base.BaseActivity;
import com.lihang.selfmvvm.base.NormalViewModel;
import com.lihang.selfmvvm.common.SystemConst;
import com.lihang.selfmvvm.databinding.ActivityWelcomBinding;
import com.lihang.selfmvvm.ui.MainActivity;
import com.lihang.selfmvvm.ui.demo.home.HomeActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by leo
 * on 2019/11/12.
 */
public class WelComeActivity extends BaseActivity<NormalViewModel, ActivityWelcomBinding> {
    @Override
    protected int getContentViewId() {
        return R.layout.activity_welcom;
    }

    @Override
    protected void processLogic() {
        isRelease();
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha_welcome);
        binding.txt.startAnimation(animation);

        binding.imgSvg.setViewportSize(167, 220);
        binding.imgSvg.setTraceColor(getResources().getColor(R.color.white));
        binding.imgSvg.start();

        Observable.timer(2500, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(aLong -> {
            ActivitysBuilder.build(WelComeActivity.this, HomeActivity.class)
                    .withAnimal(0, com.leo.utilspro.R.anim.anim_translate_hide)
                    .startActivity();
        });


    }

    private void isRelease() {
        if (BuildConfig.BUILD_TYPE.equals("debug")) {
            if (SystemConst.DEFAULT_SERVER_DEBUG.equals("http://192.168.5.102:9088/")) {
                SystemConst.HTML_ARTDETAIL = SystemConst.HTML_ARTDETAIL_TEST;
                SystemConst.HTML_GAME = SystemConst.HTML_GAME_TEST;
            }
        } else {
            SystemConst.HTML_ARTDETAIL = SystemConst.HTML_ARTDETAIL_RELEASE;
            SystemConst.HTML_GAME = SystemConst.HTML_GAME_RELEASE;
            //线上版本不让打印
            LogUtils.isEnableLog = false;
        }
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onClick(View v) {

    }
}
