package com.lihang.selfmvvm.ui.demo.fragment.home;

import android.os.Bundle;
import android.view.View;

import com.leo.utilspro.utils.ActivitysBuilder;
import com.leo.utilspro.utils.DataUtils;
import com.lihang.nbadapter.BaseAdapter;
import com.lihang.selfmvvm.R;
import com.lihang.selfmvvm.base.BaseFragment;
import com.lihang.selfmvvm.base.bean.ParamsBuilder;
import com.lihang.selfmvvm.bean.BannerBean;
import com.lihang.selfmvvm.bean.HomeBean;
import com.lihang.selfmvvm.bean.HomeFatherBean;
import com.lihang.selfmvvm.databinding.HomeFragmentBinding;
import com.lihang.selfmvvm.ui.demo.activity.WebActivity;
import com.lihang.selfmvvm.ui.demo.funexplain.bannerintro.ImageAdapter;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.AlphaPageTransformer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo
 * on 2021/9/26.
 */
public class HomeFragment extends BaseFragment<HomeViewModel, HomeFragmentBinding> implements BaseAdapter.OnItemClickListener<HomeBean> {
    private com.lihang.selfmvvm.ui.home.adapter.HomeAdapter adapter;
    private int curPage = 0;
    private ArrayList<HomeBean> homeBeans = new ArrayList<>();
    private ArrayList<BannerBean> bannerBeans = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.home_fragment;
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

        adapter = new com.lihang.selfmvvm.ui.home.adapter.HomeAdapter(this);
        adapter.setOnItemClickListener(this);
        adapter.setDataList(homeBeans);
        binding.recyclerView.setAdapter(adapter);

        getBanner();
        getHomeArticles(curPage, null);
    }

    @Override
    protected void setListener() {
        binding.smartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            curPage = 0;
            getHomeArticles(curPage, ParamsBuilder.build().isShowDialog(false));
        });

        binding.smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            curPage++;
            getHomeArticles(curPage, ParamsBuilder.build().isShowDialog(false));
        });
    }

    @Override
    public void onClick(View v) {

    }


    private void getBanner() {
        mViewModel.getBanner().observe(this, resource -> resource.handler(new OnCallback<List<BannerBean>>() {
            @Override
            public void onSuccess(List<BannerBean> data) {
                bannerBeans.addAll(data);
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
         */
        binding.banner.setAdapter(new ImageAdapter(data))
                .addBannerLifecycleObserver(this)
                .setIndicator(new CircleIndicator(getActivity()))
                .setBannerGalleryEffect(12, 6)
                .addPageTransformer(new AlphaPageTransformer(0.8f));

        binding.banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(Object data, int position) {
                ActivitysBuilder.build(HomeFragment.this, WebActivity.class)
                        .putExtra("url", ((BannerBean) data).getUrl())
                        .startActivity();
            }
        });
    }


    private void getHomeArticles(int currenPage, ParamsBuilder paramsBuilder) {
        mViewModel.getHomeArticles(currenPage, paramsBuilder).observe(this, resource -> resource.handler(new OnCallback<HomeFatherBean>() {
            @Override
            public void onSuccess(HomeFatherBean data) {
                DataUtils.initData(currenPage, homeBeans, data.getDatas(), adapter, binding.smartRefreshLayout);
            }
        }, binding.smartRefreshLayout));
    }


    @Override
    public void onItemClick(HomeBean item, int position) {
        ActivitysBuilder.build(this, WebActivity.class)
                .putExtra("url", item.getLink())
                .startActivity();
    }
}
