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
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

/**
 * Created by leo
 * on 2019/10/16.
 * 从每个页面的ViewModel里，就能看出每个页面的功能和联网请求。类似于MVP里的契约类
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

    //收藏文章
    public LiveData<Resource<String>> collectArticle(HomeBean data) {
        int id = data.getId();
        //收藏站内文章
        if (id > 0) {
            return collectArticle(id);
        }
        //收藏站外文章
        return getRepository().collectOutArticle(data.getTitle(), data.getAuthor(), data.getLink());
    }

    public LiveData<Resource<String>> unCollectByHome(int id) {
        return getRepository().unCollectByHome(id);
    }


    //收藏站内文章
    public LiveData<Resource<String>> collectArticle(int id) {
        return getRepository().collectArticle(id);
    }

    //收藏站外文章
    public LiveData<Resource<String>> collectOutArticle(String title, String author, String link) {
        return getRepository().collectOutArticle(title, author, link);
    }


    //退出登录
    public LiveData<Resource<String>> loginOut() {
        return getRepository().LoginOut();
    }

    public LiveData<Resource<File>> downFile(String destDir, String fileName) {
        return getRepository().downFile(destDir, fileName);
    }

    public LiveData<Resource<String>> upLoadPic(String type, String key, File file) {
        return getRepository().upLoadPic(type, key, file);
    }


}
