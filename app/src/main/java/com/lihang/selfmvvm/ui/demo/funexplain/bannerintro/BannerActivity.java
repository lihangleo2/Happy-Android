package com.lihang.selfmvvm.ui.demo.funexplain.bannerintro;

import android.view.View;

import com.leo.utilspro.utils.ActivitysBuilder;
import com.lihang.selfmvvm.R;
import com.lihang.selfmvvm.base.BaseActivity;
import com.lihang.selfmvvm.base.BaseFragment;
import com.lihang.selfmvvm.bean.BannerBean;
import com.lihang.selfmvvm.databinding.BannerActivityBinding;
import com.lihang.selfmvvm.ui.demo.activity.WebActivity;
import com.lihang.selfmvvm.ui.demo.fragment.home.HomeFragment;
import com.lihang.selfmvvm.ui.demo.fragment.home.HomeViewModel;
import com.youth.banner.config.BannerConfig;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.AlphaPageTransformer;
import com.youth.banner.util.BannerUtils;

import java.util.List;

/**
 * Created by leo
 * on 2021/11/9.
 */
public class BannerActivity extends BaseActivity<HomeViewModel, BannerActivityBinding> {
    @Override
    protected int getContentViewId() {
        return R.layout.banner_activity;
    }

    @Override
    protected void processLogic() {
        getBanner();
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onClick(View v) {

    }


    private void getBanner() {
        mViewModel.getBanner().observe(this, resource -> resource.handler(new OnCallback<List<BannerBean>>() {
            @Override
            public void onSuccess(List<BannerBean> data) {
                updateBanner(data);
            }
        }));
    }


    private void updateBanner(List<BannerBean> data) {
        if (data == null || data.size() <= 0) {
            return;
        }

        /**
         * 画廊效果
         * addPageTransformer 有很多种翻滚方式
         */
        binding.banner.setAdapter(new ImageAdapter(data))
                .addBannerLifecycleObserver(this)
                .setIndicator(new CircleIndicator(this))
                .setBannerGalleryEffect(12, 6)
                .addPageTransformer(new AlphaPageTransformer(0.8f));


        /**
         * 带title和指示器的
         * */

        binding.banner2.setAdapter(new ImageTitleAdapter(data))
                .addBannerLifecycleObserver(this)
                .setIndicator(new CircleIndicator(this))
                .setIndicatorGravity(IndicatorConfig.Direction.RIGHT)
                .setIndicatorMargins(new IndicatorConfig.Margins(0, 0,
                        BannerConfig.INDICATOR_MARGIN, BannerUtils.dp2px(12)));


        /**
         * 指示器在外面的
         * */
        binding.banner3.setAdapter(new ImageTitleAdapter(data))
                .addBannerLifecycleObserver(this)
                .setIndicator(binding.indicator, false);


        //banner的点击，必须在setAdapter之后
//        binding.banner.setOnBannerListener(new OnBannerListener() {
//            @Override
//            public void OnBannerClick(Object data, int position) {
//                ActivitysBuilder.build(BannerActivity.this, WebActivity.class)
//                        .putExtra("url", ((BannerBean) data).getUrl())
//                        .startActivity();
//            }
//        });


    }
}
