package com.lihang.selfmvvm.base;

import android.app.Application;

import com.lihang.selfmvvm.base.bean.ParamsBuilder;
import com.lihang.selfmvvm.base.bean.Resource;
import com.lihang.selfmvvm.bean.BannerBean;
import com.lihang.selfmvvm.bean.HomeFatherBean;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

/**
 * Created by leo
 * on 2019/11/12.
 * 不需要用ViewModel的,请用此类代替
 */
public class NormalViewModel extends BaseViewModel {

    public NormalViewModel(@NonNull Application application) {
        super(application);
    }

}
