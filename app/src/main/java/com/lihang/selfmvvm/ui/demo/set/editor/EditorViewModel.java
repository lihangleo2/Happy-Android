package com.lihang.selfmvvm.ui.demo.set.editor;

import android.app.Application;

import com.lihang.selfmvvm.base.BaseViewModel;
import com.lihang.selfmvvm.base.RepositoryImpl;
import com.lihang.selfmvvm.base.bean.ParamsBuilder;
import com.lihang.selfmvvm.base.bean.Resource;
import com.lihang.selfmvvm.bean.BannerBean;
import com.lihang.selfmvvm.bean.HomeBean;
import com.lihang.selfmvvm.bean.HomeFatherBean;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

/**
 * Created by leo
 * on 2019/10/16.
 */
public class EditorViewModel extends BaseViewModel<RepositoryImpl> {


    public EditorViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Resource<String>> upLoadFile(String key, File file, ParamsBuilder paramsBuilder) {
        return getRepository().upLoadFile(key, file, paramsBuilder);
    }

    public LiveData<Resource<List<String>>> upLoadMoreFiles(String type, String key,ArrayList<File> files, ParamsBuilder paramsBuilder) {
        //type 是form表单额外的参数
        //key 是多文件的同一key，也可以是不同key
        return getRepository().upLoadMoreFiles(type, key,files, paramsBuilder);
    }

    public LiveData<Resource<List<String>>> upLoadMoreFilesWithNoProgress(String type, String key,ArrayList<File> files, ParamsBuilder paramsBuilder) {
        return getRepository().upLoadMoreFilesWithNoProgress(type, key,files, paramsBuilder);
    }
}
