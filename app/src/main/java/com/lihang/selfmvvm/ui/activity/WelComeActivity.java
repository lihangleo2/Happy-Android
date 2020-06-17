package com.lihang.selfmvvm.ui.activity;

import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.lihang.selfmvvm.R;
import com.lihang.selfmvvm.base.BaseActivity;
import com.lihang.selfmvvm.base.NormalViewModel;
import com.lihang.selfmvvm.databinding.ActivityWelcomBinding;
import com.lihang.selfmvvm.ui.home.HomeActivity;
import com.lihang.selfmvvm.utils.ActivityUtils;
import com.lihang.selfmvvm.utils.LogUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

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
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha_welcome);
        binding.txt.startAnimation(animation);

        binding.imgSvg.setViewportSize(167, 220);
        binding.imgSvg.setTraceColor(getResources().getColor(R.color.white));
        binding.imgSvg.start();

        Observable.timer(2500, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(aLong -> {
            ActivityUtils.startActivity(WelComeActivity.this, HomeActivity.class);
            ActivityUtils.finishWithAnim(WelComeActivity.this,0,R.animator.set_anim_activity_exit);
        });
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onClick(View v) {

    }
}
