package com.lihang.selfmvvm.base;

import android.app.Application;

import androidx.annotation.NonNull;

/**
 * Created by leo
 * on 2019/11/12.
 */
public class NormalViewModel extends BaseViewModel {


    public NormalViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected BaseModel createRepository() {
        return null;
    }
}
