package com.lihang.selfmvvm.ui.home;

import android.app.Application;

import com.lihang.selfmvvm.base.BaseViewModel;
import com.lihang.selfmvvm.bean.BannerBean;
import com.lihang.selfmvvm.bean.basebean.Resource;
import com.lihang.selfmvvm.utils.LogUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

/**
 * Created by leo
 * on 2019/10/16.
 */
public class HomeViewModel extends BaseViewModel<HomeRepository> {


    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected HomeRepository createRepository() {
        return new HomeRepository();
    }


    public LiveData<Resource<List<BannerBean>>> getBanner() {
        return getRepository().getBannerList();
    }

    public LiveData<Resource<File>> downFile(String destDir, String fileName) {
        return getRepository().downFile(destDir,fileName);
    }

    public LiveData<Resource<String>> uoLoad(HashMap<String,String> map, Map<String, File> files) {
        return getRepository().upLoad(map,files);
    }

}
