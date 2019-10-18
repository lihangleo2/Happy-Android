package com.lihang.selfmvvm.ui.home;

import android.os.Environment;
import android.view.View;

import com.lihang.selfmvvm.R;
import com.lihang.selfmvvm.base.BaseActivity;
import com.lihang.selfmvvm.bean.BannerBean;
import com.lihang.selfmvvm.databinding.ActivityHomeTestBinding;
import com.lihang.selfmvvm.utils.GlideImageLoader;
import com.lihang.selfmvvm.utils.LogUtils;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by leo
 * on 2019/10/15.
 */
public class HomeActivity extends BaseActivity<HomeViewModel, ActivityHomeTestBinding> {

    @Override
    protected int getContentViewId() {
        return R.layout.activity_home_test;
    }

    @Override
    protected void processLogic() {
        binding.setOnclickListener(this);
        initBanner();
        getBanner();
    }

    private void getBanner() {
        mViewModel.getBanner().observe(this, resource -> resource.handler(new OnCallback<List<BannerBean>>() {
            @Override
            public void onSuccess(List<BannerBean> data) {
                updateBanner(data);
            }
        }));
    }




    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.banner.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        binding.banner.stopAutoPlay();
    }

    private void updateBanner(List<BannerBean> data) {
        if (data == null || data.size() <= 0) {
            return;
        }
        List<String> urls = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            urls.add(data.get(i).getImagePath());
            titles.add(data.get(i).getTitle());
        }
        binding.banner.setBannerTitles(titles);
        binding.banner.setImages(urls);
        binding.banner.start();
    }

    private void initBanner() {
        binding.banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        binding.banner.setImageLoader(new GlideImageLoader());
    }

}
