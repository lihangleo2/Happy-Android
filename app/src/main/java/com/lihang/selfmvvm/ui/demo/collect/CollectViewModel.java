package com.lihang.selfmvvm.ui.demo.collect;

import android.app.Application;

import com.lihang.selfmvvm.base.BaseViewModel;
import com.lihang.selfmvvm.base.RepositoryImpl;
import com.lihang.selfmvvm.bean.basebean.HomeFatherBean;
import com.lihang.selfmvvm.bean.basebean.ParamsBuilder;
import com.lihang.selfmvvm.bean.basebean.Resource;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

/**
 * Created by leo
 * on 2019/11/14.
 */
public class CollectViewModel extends BaseViewModel<RepositoryImpl> {

    public CollectViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Resource<HomeFatherBean>> getCollectArticles(int curPage, ParamsBuilder paramsBuilder) {
        return getRepository().getCollectArticles(curPage, paramsBuilder);
    }

    public LiveData<Resource<String>> unCollectByMe(int id, int originId) {
        return getRepository().unCollectByMe(id, originId);
    }

}
