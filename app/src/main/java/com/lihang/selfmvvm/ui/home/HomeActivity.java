package com.lihang.selfmvvm.ui.home;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;

import com.lihang.nbadapter.BaseAdapter;
import com.lihang.selfmvvm.R;
import com.lihang.selfmvvm.base.BaseActivity;
import com.lihang.selfmvvm.bean.BannerBean;
import com.lihang.selfmvvm.bean.HomeBean;
import com.lihang.selfmvvm.bean.basebean.HomeFatherBean;
import com.lihang.selfmvvm.bean.basebean.ParamsBuilder;
import com.lihang.selfmvvm.databinding.ActivityHomeTestBinding;
import com.lihang.selfmvvm.ui.WebActivity;
import com.lihang.selfmvvm.utils.GlideImageLoader;
import com.youth.banner.BannerConfig;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by leo
 * on 2019/10/15.
 */
public class HomeActivity extends BaseActivity<HomeViewModel, ActivityHomeTestBinding> implements BaseAdapter.OnItemClickListener<HomeBean> {

    private HomeAdapter adapter;
    private int curPage = 0;
    private ArrayList<HomeBean> homeBeans = new ArrayList<>();
    private ArrayList<BannerBean> bannerBeans = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.activity_home_test;
    }

    @Override
    protected void processLogic() {
        binding.setOnclickListener(this);
        initBanner();
        getBanner();
        getHomeArticles(curPage, null);
        adapter = new HomeAdapter(this);
        adapter.setOnItemClickListener(this);
        adapter.setDataList(homeBeans);
        binding.recyclerView.setAdapter(adapter);

    }

    @Override
    protected void setListener() {
        binding.banner.setOnBannerListener(position -> {
            Intent intent = new Intent(this, WebActivity.class);
            intent.putExtra("url", bannerBeans.get(position).getUrl());
            startActivity(intent);
        });
        binding.smartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            curPage = 0;
            getHomeArticles(curPage, ParamsBuilder.build().isShowDialog(false));
        });

        binding.smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            curPage++;
            getHomeArticles(curPage, ParamsBuilder.build().isShowDialog(false));
        });

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

    private void getHomeArticles(int currenPage, ParamsBuilder paramsBuilder) {
        mViewModel.getHomeArticles(currenPage, paramsBuilder).observe(this, resource -> resource.handler(new OnCallback<HomeFatherBean>() {
            @Override
            public void onSuccess(HomeFatherBean data) {
                if (data.getDatas().size() > 0) {
                    if (currenPage == 0) {
                        homeBeans.clear();
                    }
                    homeBeans.addAll(data.getDatas());
                    adapter.notifyItemRangeChanged(homeBeans.size() - data.getDatas().size(), data.getDatas().size());
                } else {
                    binding.smartRefreshLayout.finishLoadMoreWithNoMoreData();
                }
            }
        }, binding.smartRefreshLayout));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_zan:
                String tag = (String) v.getTag();
                ImageView imageView = (ImageView) v;
                if (tag.equals("0")) {
                    imageView.setImageResource(R.drawable.zan_click_userdetail);
                    AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getDrawable();
                    animationDrawable.start();
                    imageView.setTag("1");
                } else {
                    imageView.setImageResource(R.mipmap.card_zan_1);
                    imageView.setTag("0");
                }
                break;
        }
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


    @Override
    public void onItemClick(HomeBean item, int position) {
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra("url", item.getLink());
        startActivity(intent);
    }
}
