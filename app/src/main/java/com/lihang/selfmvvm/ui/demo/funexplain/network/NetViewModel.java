package com.lihang.selfmvvm.ui.demo.funexplain.network;

import android.app.Application;

import com.lihang.selfmvvm.base.BaseViewModel;
import com.lihang.selfmvvm.base.RepositoryImpl;
import com.lihang.selfmvvm.base.bean.Resource;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

/**
 * Created by leo
 * on 2019/11/12.
 */
public class NetViewModel extends BaseViewModel<RepositoryImpl> {

    public NetViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Resource<File>> downFile(String url, String destDir, String fileName) {
        return getRepository().downFile(destDir, fileName,url);
    }

}
