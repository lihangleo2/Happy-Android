package com.lihang.selfmvvm.ui.home;

import android.graphics.drawable.AnimationDrawable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.gyf.barlibrary.ImmersionBar;
import com.lihang.nbadapter.BaseAdapter;
import com.lihang.selfmvvm.MyApplication;
import com.lihang.selfmvvm.R;
import com.lihang.selfmvvm.aop.NeedLogin;
import com.lihang.selfmvvm.base.BaseActivity;
import com.lihang.selfmvvm.bean.BannerBean;
import com.lihang.selfmvvm.bean.HomeBean;
import com.lihang.selfmvvm.bean.basebean.EventBusBean;
import com.lihang.selfmvvm.bean.basebean.HomeFatherBean;
import com.lihang.selfmvvm.bean.basebean.ParamsBuilder;
import com.lihang.selfmvvm.customview.iosdialog.DialogUtil;
import com.lihang.selfmvvm.databinding.ActivityHomeTestBinding;
import com.lihang.selfmvvm.ui.activity.WebActivity;
import com.lihang.selfmvvm.ui.collect.CollectActivity;
import com.lihang.selfmvvm.ui.home.adapter.HomeAdapter;
import com.lihang.selfmvvm.ui.login.LoginActivity;
import com.lihang.selfmvvm.utils.ActivityUtils;
import com.lihang.selfmvvm.utils.ButtonClickUtils;
import com.lihang.selfmvvm.utils.GlideImageLoader;
import com.lihang.selfmvvm.utils.LogUtils;
import com.lihang.selfmvvm.utils.ToastUtils;
import com.youth.banner.BannerConfig;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;


/**
 * Created by leo
 * on 2019/10/15.
 */
public class HomeActivity extends BaseActivity<HomeViewModel, ActivityHomeTestBinding> implements BaseAdapter.OnItemClickListener<HomeBean> {

    private HomeAdapter adapter;
    private int curPage = 0;
    private ArrayList<HomeBean> homeBeans = new ArrayList<>();
    private ArrayList<BannerBean> bannerBeans = new ArrayList<>();
    //沉浸式状态栏
    protected ImmersionBar mImmersionBar;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_home_test;
    }


    public void openDrawLayout() {
        if (binding.drawerLayout.isDrawerOpen(binding.txtTest)) {
            binding.drawerLayout.closeDrawer(binding.txtTest);
        } else {
            binding.drawerLayout.openDrawer(binding.txtTest);
        }
    }

    @Override
    protected void processLogic() {
        EventBus.getDefault().register(this);
        initStatusBar();
        //关闭手势运动
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
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
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initStatusBar() {
        mImmersionBar = ImmersionBar.with(this);
//        mImmersionBar = ImmersionBar.with(this)
//                .statusBarColor(R.color.yellow)
//                .keyboardEnable(true)
//                .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
//                .fitsSystemWindows(true);
        mImmersionBar.init();
    }

    @Override
    protected void setListener() {
        binding.banner.setOnBannerListener(position -> {
            startActivity(ActivityUtils.build(this, WebActivity.class)
                    .putExtra("url", bannerBeans.get(position).getUrl()));
        });
        binding.smartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            curPage = 0;
            getHomeArticles(curPage, ParamsBuilder.build().isShowDialog(false));
        });

        binding.smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            curPage++;
            getHomeArticles(curPage, ParamsBuilder.build().isShowDialog(false));
        });

        binding.leoTitleBar.bar_left_btn.setOnClickListener(this);
        binding.leoTitleBar.bar_right_btn.setOnClickListener(this);

        binding.drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                View mContent = binding.drawerLayout.getChildAt(0);
                mContent.setTranslationX(drawerView.getWidth() * slideOffset);
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
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

    public void collectArt(HomeBean homeBean) {
        mViewModel.collectArticle(homeBean).observe(this, resource -> {
            resource.handler(new OnCallback<String>() {
                @Override
                public void onSuccess(String data) {

                }
            });
        });
    }

    public void unCollectArt(int id) {
        mViewModel.unCollectByHome(id).observe(this, resource -> {
            resource.handler(new OnCallback<String>() {
                @Override
                public void onSuccess(String data) {

                }
            });
        });
    }


    @Override
    public void onClick(View v) {
        if (ButtonClickUtils.isFastClick()) {
            return;
        }
        switch (v.getId()) {
            case R.id.image_zan:
                followBog(v);
                break;
            case R.id.bar_left_btn:
                openMymessage();
                break;

            case R.id.bar_right_btn:
                ToastUtils.showToast("该功能还在路上");
                break;

            case R.id.txt_loginOut:
                DialogUtil.alertIosDialog(HomeActivity.this, "是否退出登录？", "确定", "取消", () -> {
                    mViewModel.loginOut().observe(this, resource -> {
                        resource.handler(new OnCallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                MyApplication.logOut();
                                binding.drawerLayout.closeDrawer(binding.txtTest);
                                binding.smartRefreshLayout.autoRefresh();
                            }
                        });
                    });
                });
                break;
            case R.id.txt_collect:
                ActivityUtils.startActivity(this, CollectActivity.class);
                break;
        }
    }

    @NeedLogin
    private void openMymessage() {
        binding.txtName.setText(MyApplication.getLoginUser().getPublicName());
        openDrawLayout();
    }

    @NeedLogin
    private void followBog(View v) {
        ImageView imageView = (ImageView) v;
        HomeBean homeBean = (HomeBean) v.getTag();
        if (homeBean.isCollect()) {
            imageView.setImageResource(R.mipmap.card_zan_1);
            homeBean.setCollect(false);
            unCollectArt(homeBean.getId());
        } else {
            imageView.setImageResource(R.drawable.zan_click_userdetail);
            AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getDrawable();
            animationDrawable.start();
            homeBean.setCollect(true);
            collectArt(homeBean);
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
        startActivity(ActivityUtils.build(this, WebActivity.class)
                .putExtra("url", item.getLink()));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            //点击返回键，不退出应用程序。直接返回后台
            ActivityUtils.startHome();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onbackEvent(EventBusBean eventBusBean) {
        switch (eventBusBean.getType()) {
            case 1:
                binding.smartRefreshLayout.autoRefresh();
                break;
            case 2:
                int id = (int) eventBusBean.getValue();
                for (int i = 0; i < homeBeans.size(); i++) {
                    if (homeBeans.get(i).getChapterId() == id) {
                        homeBeans.get(i).setCollect(false);
                        adapter.notifyItemChanged(i);
                    }
                }
                break;
        }
    }
}
