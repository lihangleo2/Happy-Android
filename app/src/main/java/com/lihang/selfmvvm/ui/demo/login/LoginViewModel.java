package com.lihang.selfmvvm.ui.login;

import android.app.Application;

import com.lihang.selfmvvm.base.BaseViewModel;
import com.lihang.selfmvvm.base.RepositoryImpl;
import com.lihang.selfmvvm.bean.BannerBean;
import com.lihang.selfmvvm.bean.User;
import com.lihang.selfmvvm.bean.basebean.ParamsBuilder;
import com.lihang.selfmvvm.bean.basebean.Resource;

import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

/**
 * Created by leo
 * on 2019/11/12.
 */
public class LoginViewModel extends BaseViewModel<RepositoryImpl> {

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Resource<User>> login(HashMap<String, Object> map, ParamsBuilder paramsBuilder) {
        return getRepository().login(map, paramsBuilder);
    }
}
