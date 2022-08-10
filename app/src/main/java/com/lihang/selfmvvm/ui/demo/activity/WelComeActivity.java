package com.lihang.selfmvvm.ui.demo.activity;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.leo.utilspro.utils.ActivitysBuilder;
import com.lihang.selfmvvm.R;
import com.lihang.selfmvvm.base.BaseActivity;
import com.lihang.selfmvvm.base.NormalViewModel;
import com.lihang.selfmvvm.databinding.WelcomActivityBinding;
import com.lihang.selfmvvm.ui.MainActivity;
import com.lihang.selfmvvm.ui.demo.login.LoginActivity;
import com.lihang.selfmvvm.utils.AppUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by leo
 * on 2019/11/12.
 */
public class WelComeActivity extends BaseActivity<NormalViewModel, WelcomActivityBinding> {
    @Override
    protected int getContentViewId() {
        return R.layout.welcom_activity;
    }

    @Override
    protected void processLogic() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha_welcome);
        binding.txt.startAnimation(animation);

        binding.imgSvg.setViewportSize(167, 220);
        binding.imgSvg.setTraceColor(getResources().getColor(R.color.white));
        binding.imgSvg.start();


        Observable.timer(2500, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(aLong -> {
            Class goClass;
            if (AppUtils.isLogin()) {
                goClass = MainActivity.class;
            } else {
                goClass = LoginActivity.class;
            }
            ActivitysBuilder.build(WelComeActivity.this, goClass)//HomeActivity
                    .withAnimal(0, com.leo.utilspro.R.anim.anim_translate_hide)
                    .finish(true)
                    .startActivity();
        });


    }


    @Override
    protected void setListener() {

    }

    @Override
    public void onClick(View v) {

    }
}
