package com.lihang.selfmvvm.base;

import android.app.Application;

import com.lihang.selfmvvm.utils.LogUtils;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by leo
 * on 2019/10/15.
 */

public abstract class BaseViewModel<T extends BaseModel> extends AndroidViewModel {
    //这个是为了退出页面，取消请求的
    public CompositeDisposable compositeDisposable;
    private T repository;
    private ArrayList<String> onNetTags;

    protected abstract T createRepository();

    public BaseViewModel(@NonNull Application application) {
        super(application);
        this.repository = createRepository();
        compositeDisposable = new CompositeDisposable();
        onNetTags = new ArrayList<>();
    }

    public void setObjectLifecycleTransformer(LifecycleTransformer objectLifecycleTransformer) {
        repository.setObjectLifecycleTransformer(objectLifecycleTransformer);
        repository.setCompositeDisposable(compositeDisposable);
        repository.setOnNetTags(onNetTags);
    }

    public T getRepository() {
        return repository;
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        //销毁后，取消当前页所有在执行的网络请求。
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }
}
