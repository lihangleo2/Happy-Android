package com.lihang.selfmvvm.ui.demo.fragment.home;

import android.app.Application;

import com.lihang.selfmvvm.base.BaseViewModel;
import com.lihang.selfmvvm.base.RepositoryImpl;
import com.lihang.selfmvvm.bean.BannerBean;
import com.lihang.selfmvvm.bean.HomeBean;
import com.lihang.selfmvvm.bean.HomeFatherBean;
import com.lihang.selfmvvm.base.bean.ParamsBuilder;
import com.lihang.selfmvvm.base.bean.Resource;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

/**
 * Created by leo
 * on 2019/10/16.
 * 1、从每个页面的ViewModel里，就能看出每个页面的功能和联网请求。类似于MVP里的契约类
 * 2、如果横竖屏转换的时候，也可以使用HomeViewModel进行一个保存
 */
public class HomeViewModel extends BaseViewModel<RepositoryImpl> {


    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    //获取banner轮播
    public LiveData<Resource<List<BannerBean>>> getBanner() {
        return getRepository().getBannerList();
    }

    //获取首页文章
    public LiveData<Resource<HomeFatherBean>> getHomeArticles(int curPage, ParamsBuilder paramsBuilder) {
        return getRepository().getHomeArticles(curPage, paramsBuilder);
    }

}
